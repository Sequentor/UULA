package ru.sequentor.uula.ui.fragment

import kotlinx.coroutines.*

class LessonsFragmentPresenter(
    private val view: LessonsFragmentContract.View,
    private val scope: CoroutineScope
) : LessonsFragmentContract.Presenter {

    private val model = LessonsFragmentModel()

    override fun getLessons(pageNumber: Int) {

        view.setLoader(true)

        scope.launch {

            // ONLY FOR TEST (Delay for full progress animation)
            delay(500)

            val response = withContext(Dispatchers.IO) {
                model.getLessons(pageNumber)
            }

            if (response.isSuccessful) {

                if (response.body()!!.isNotEmpty()) {

                    val data = model.parseData(response.body()!!)
                    val lessonsProgress = model.getLessonsProgress(data)
                    view.updateRecyclerViewData(data)
                    view.showLessonsProgress(lessonsProgress)

                } else {

                    view.showResponseEmpty()

                }

            } else {

                view.showResponseError(response.code())

            }

            view.setLoader(false)
        }
    }
}