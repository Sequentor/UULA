package ru.sequentor.uula.adapter

import ru.sequentor.uula.model.RVData

interface RecyclerViewAdapterInterface {
    fun onRecyclerViewItemClick(data: RVData)
}