package ru.sequentor.uula.network


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sequentor.uula.application.App
import ru.sequentor.uula.model.Lesson
import ru.sequentor.uula.struct.Constants.baseURL
import java.io.IOException


object ApiFactory {

    private val apiService: ApiService

    init {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .cache(Cache(App.getContext().cacheDir, 10 * 1024 * 1024))
            .addInterceptor(logging)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request: Request = chain.request()
                    request = if (isNetworkConnected()) {
                        request.newBuilder()
                            .header(
                                "Authorization",
                                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTA4LCJleH"
                            ).header("Cache-Control", "public, max-age=60").build()
                    } else {
                        request.newBuilder()
                            .header(
                                "Authorization",
                                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTA4LCJleH"
                            ).header(
                                "Cache-Control",
                                "public, only-if-cached, max-stale=604800"
                            ).build()
                    }
                    return chain.proceed(request)
                }
            })

        val retrofit: Retrofit =
            Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()

        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun getLessons(pageNumber: Int): retrofit2.Response<List<Lesson>> {
        return apiService.getLessons(pageNumber)
    }
}