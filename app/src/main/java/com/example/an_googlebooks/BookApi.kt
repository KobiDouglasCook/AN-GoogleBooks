package com.example.an_googlebooks


data class BookApi(private val search: String) {
    private val base = "https://www.googleapis.com/books/v1/volumes?q="
    val getUrl: String
        get() = base + search
}
