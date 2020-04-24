package ru.sequentor.uula.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.fragment_lessons.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.sequentor.uula.R
import ru.sequentor.uula.adapter.RecyclerViewAdapter
import ru.sequentor.uula.adapter.RecyclerViewAdapterInterface
import ru.sequentor.uula.model.LessonsProgress
import ru.sequentor.uula.model.RVData
import ru.sequentor.uula.ui.activity.MainActivity
import ru.sequentor.uula.ui.dialog.AlertDialogBuilder
import kotlin.coroutines.CoroutineContext


class LessonsFragment : Fragment(), CoroutineScope,
    RecyclerViewAdapterInterface, LessonsFragmentContract.View {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private var currentPage: Int = 1
    lateinit var lessonsFragmentPresenter: LessonsFragmentContract.Presenter
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lessons, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPage()
    }

    fun RecyclerView.apply(): RecyclerViewAdapter {
        val genericAdapter = object : RecyclerViewAdapter() {}
        genericAdapter.recyclerViewAdapterInterface = this@LessonsFragment
        layoutManager = LinearLayoutManager(context)
        adapter = genericAdapter
        return genericAdapter
    }

    private fun setToolbarTitle(toolbarTitle: String = "", toolbarSubTitle: String = "") {
        with(activity as MainActivity) {
            title = toolbarTitle
            toolbar_layout.subtitle = toolbarSubTitle
        }
    }

    private fun initPage() {
        setToolbarTitle(
            resources.getString(R.string.toolbar_title),
            resources.getString(R.string.toolbar_subtitle, (1..12).random(), (1..6).random())
        )
        recyclerViewAdapter = fragment_lessons_recycler.apply()

        lessonsFragmentPresenter = LessonsFragmentPresenter(this, this)
        lessonsFragmentPresenter.getLessons(currentPage)

        setButtonVisibility()
        button_previous.setOnClickListener { buttonPreviousClick() }
        button_next.setOnClickListener { buttonNextClick() }
    }

    private fun setButtonVisibility() {

        if (currentPage == 1 || currentPage < 11) {
            button_next.visibility = View.VISIBLE
        } else {
            button_next.visibility = View.INVISIBLE
        }

        if (currentPage > 1) {
            button_previous.visibility = View.VISIBLE
        } else {
            button_previous.visibility = View.INVISIBLE
        }
    }

    private fun buttonPreviousClick() {
        currentPage--
        lessonsFragmentPresenter.getLessons(currentPage)
    }

    private fun buttonNextClick() {
        currentPage++
        lessonsFragmentPresenter.getLessons(currentPage)
    }

    override fun onRecyclerViewItemClick(data: RVData) {
        Toast.makeText(
            context,
            resources.getString(R.string.click) + data,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun setLoader(state: Boolean) {
        (activity as MainActivity).loader?.let {
            if (state) {
                it.animate().alpha(1f)
            } else {
                it.animate().alpha(0f)
            }
        }
    }

    override fun updateRecyclerViewData(data: List<RVData>) {
        // Need to invalidate ViewHolders
        recyclerViewAdapter = fragment_lessons_recycler.apply()

        recyclerViewAdapter.update(data)
        activity?.nested_scroll?.scrollTo(0, 0)
        setButtonVisibility()
    }

    override fun showResponseError(code: Int) {

        AlertDialogBuilder.showError(
            context = context!!,
            title = resources.getString(R.string.response_error),
            message = resources.getString(R.string.error, code),
            buttonPositive = resources.getString(R.string.retry),
            buttonNegative = resources.getString(R.string.ok),
            positive = {
                currentPage = 1
                lessonsFragmentPresenter.getLessons(currentPage)
            })
    }

    override fun showResponseEmpty() {

        AlertDialogBuilder.showError(
            context = context!!,
            title = resources.getString(R.string.data_is_empty),
            message = resources.getString(R.string.data_is_empty_message),
            buttonPositive = resources.getString(R.string.next),
            buttonNegative = resources.getString(R.string.previous),
            positive = {
                buttonNextClick()
            },
            negative = {
                buttonPreviousClick()
            })
    }

    @SuppressLint("SetTextI18n")
    override fun showLessonsProgress(lessonsProgress: LessonsProgress) {
        progress.text = "${lessonsProgress.current} / ${lessonsProgress.max}"
    }
}