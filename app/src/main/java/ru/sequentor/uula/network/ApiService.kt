package ru.sequentor.uula.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.sequentor.uula.model.Lesson

interface ApiService {

    @GET("tests/lessons")
    suspend fun getLessons(@Query("page") pageNumber: Int): Response<List<Lesson>>
}