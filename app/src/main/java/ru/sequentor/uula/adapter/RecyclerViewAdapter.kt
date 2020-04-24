package ru.sequentor.uula.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import ru.sequentor.uula.R
import ru.sequentor.uula.model.OfflineMaterial
import ru.sequentor.uula.model.RVData
import ru.sequentor.uula.model.Survey
import ru.sequentor.uula.model.Video
import ru.sequentor.uula.utils.initGlideCorners
import ru.sequentor.uula.utils.setCompoundDrawable


@Suppress("UNCHECKED_CAST")
abstract class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewHolder>() {

    var recyclerViewAdapterInterface: RecyclerViewAdapterInterface? = null

    var dataList = mutableListOf<RVData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                viewType,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int = R.layout.recycler_view_item

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val data = dataList[position]

        with(holder.itemView) {

            when (data) {

                is Survey -> {
                    title.text = data.title
                    sub_title.text = resources.getString(R.string.questions, data.questions_count)
                    preview_layout_bg.setBackgroundResource(R.drawable.recycler_view_item_shape_quiz)
                    setCompoundDrawable(sub_title, R.drawable.ic_file_grey_24dp)
                    preview_icon.setBackgroundResource(R.drawable.ic_pdf_white_48dp)
                    preview_title.text = resources.getString(R.string.quiz)
                    if (data.visited) {
                        preview_layout_checkbox.setBackgroundResource(R.drawable.ic_check_circle_purple_24dp)
                    } else {
                        preview_layout_checkbox.setImageDrawable(null)
                    }
                }
                is OfflineMaterial -> {
                    title.text = data.title
                    sub_title.text = data.format
                    preview_layout_bg.setBackgroundResource(R.drawable.recycler_view_item_shape_material)
                    setCompoundDrawable(sub_title, R.drawable.ic_file_grey_24dp)
                    preview_icon.setBackgroundResource(R.drawable.ic_pdf_white_48dp)
                    preview_title.text = resources.getString(R.string.offline_material)
                    if (data.visited) {
                        preview_layout_checkbox.setBackgroundResource(R.drawable.ic_check_circle_purple_24dp)
                    } else {
                        preview_layout_checkbox.setImageDrawable(null)
                    }
                }
                is Video -> {
                    title.text = data.title
                    sub_title.text = resources.getString(R.string.comments, data.comments_count)
                    initGlideCorners(preview_layout_bg, data.images.medium, 12)
                    setCompoundDrawable(sub_title, R.drawable.ic_message_grey_24dp)
                    preview_icon.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp)
                    if (data.visited) {
                        preview_layout_checkbox.setBackgroundResource(R.drawable.ic_check_circle_purple_24dp)
                    } else {
                        preview_layout_checkbox.setImageDrawable(null)
                    }
                }
                else -> {
                }
            }

            setOnClickListener { recyclerViewAdapterInterface?.onRecyclerViewItemClick(data) }

        }
    }

    fun update(list: List<RVData>) {
        dataList = list.toMutableList()
        notifyDataSetChanged()
    }
}

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)