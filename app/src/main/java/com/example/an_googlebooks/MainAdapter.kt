package com.example.an_googlebooks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_book_row.view.*

class MainAdapter(private val books: ArrayList<Book>, private val callback: ((Int) -> Unit)) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private lateinit var context: Context

    //called once when view holder init
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.main_book_row, parent, false)
        return MainViewHolder(row).listen { pos, _ ->
            callback(pos)
        }
    }

    //called each item (row) for RV
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(books[position])
    }

    //call once during start of lifecycle
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    //number of items (rows)
    override fun getItemCount(): Int = books.size

    //unique value per item
    override fun getItemId(position: Int): Long = position.toLong()

    //ADAPTER - ViewHolder
    inner class MainViewHolder(private val row: View): RecyclerView.ViewHolder(row) {
        fun bind(book: Book) {
            row.book_main_label.text = book.info.title
            row.book_sub_label.text = book.info.description
            if (book.info.image != null) {
                //download image using Glide Api
                Glide.with(context).load(book.info.image.thumbnail).into(row.book_image)
            }
        }
    }

}