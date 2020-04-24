package ru.sequentor.uula.data

import retrofit2.Response
import ru.sequentor.uula.model.*
import ru.sequentor.uula.network.ApiFactory
import ru.sequentor.uula.utils.parseJSON

object DataManager {

    suspend fun getLessons(pageNumber: Int): Response<List<Lesson>> {
        return ApiFactory.getLessons(pageNumber)
    }

    fun getLessonsProgress(list: List<RVData>): LessonsProgress {

        val lessonsProgress = LessonsProgress()

        list.forEach {

            when (it) {
                is Survey -> {
                    if (it.visited) {
                        lessonsProgress.current++
                    }
                    lessonsProgress.max++
                }
                is OfflineMaterial -> {
                    if (it.visited) {
                        lessonsProgress.current++
                    }
                    lessonsProgress.max++
                }
                is Video -> {
                    if (it.visited) {
                        lessonsProgress.current++
                    }
                    lessonsProgress.max++
                }
            }

        }

        return lessonsProgress
    }

    fun parseData(list: List<Lesson>): List<RVData> {

        val dataList = mutableListOf<RVData>()

        list.forEach {

            when (it.kind) {
                "Survey" -> dataList.add(parseJSON<Survey>(it.item))
                "OfflineMaterial" -> dataList.add(parseJSON<OfflineMaterial>(it.item))
                "Video" -> dataList.add(parseJSON<Video>(it.item))
            }
        }

        return dataList
    }
}