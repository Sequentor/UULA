package ru.sequentor.uula.ui.fragment

import retrofit2.Response
import ru.sequentor.uula.model.Lesson
import ru.sequentor.uula.model.LessonsProgress
import ru.sequentor.uula.model.RVData

interface LessonsFragmentContract {

    interface View {

        fun setLoader(state: Boolean)

        fun updateRecyclerViewData(data: List<RVData>)

        fun showResponseError(code: Int)

        fun showResponseEmpty()

        fun showLessonsProgress(lessonsProgress: LessonsProgress)
    }

    interface Presenter {

        fun getLessons(pageNumber: Int)
    }

    interface Model {

        suspend fun getLessons(pageNumber: Int): Response<List<Lesson>>

        suspend fun parseData(list: List<Lesson>): List<RVData>

        suspend fun getLessonsProgress(list: List<RVData>): LessonsProgress
    }
}