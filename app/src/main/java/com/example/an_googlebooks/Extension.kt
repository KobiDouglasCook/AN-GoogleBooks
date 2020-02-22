package com.example.an_googlebooks

import androidx.recyclerview.widget.RecyclerView

//RecyclerView
fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}