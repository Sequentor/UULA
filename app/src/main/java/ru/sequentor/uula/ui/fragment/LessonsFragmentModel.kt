package ru.sequentor.uula.ui.fragment

import retrofit2.Response
import ru.sequentor.uula.data.DataManager
import ru.sequentor.uula.model.Lesson
import ru.sequentor.uula.model.LessonsProgress
import ru.sequentor.uula.model.RVData

class LessonsFragmentModel : LessonsFragmentContract.Model {

    override suspend fun getLessons(pageNumber: Int): Response<List<Lesson>> {
        return DataManager.getLessons(pageNumber)
    }

    override suspend fun parseData(list: List<Lesson>): List<RVData> {
        return DataManager.parseData(list)
    }

    override suspend fun getLessonsProgress(list: List<RVData>): LessonsProgress {
        return DataManager.getLessonsProgress(list)
    }
}