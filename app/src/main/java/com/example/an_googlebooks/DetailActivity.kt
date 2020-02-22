package com.example.an_googlebooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var book: Book

    private lateinit var bookImage: ImageView
    private lateinit var bookTitle: TextView
    private lateinit var bookAuthors: TextView
    private lateinit var bookDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupDetail()
    }

    private fun setupDetail() {
        book = intent.getSerializableExtra("book") as Book
        bookImage = findViewById(R.id.book_image)
        bookTitle = findViewById(R.id.book_title)
        bookAuthors = findViewById(R.id.book_authors)
        bookDescription = findViewById(R.id.book_description)

        book.info.image?.let {
            Glide.with(this).load(it.thumbnail).into(bookImage)
        }

        bookTitle.text = book.info.title
        bookAuthors.text = book.info.authors.joinToString(", ")
        bookDescription.text = book.info.description
    }
}
