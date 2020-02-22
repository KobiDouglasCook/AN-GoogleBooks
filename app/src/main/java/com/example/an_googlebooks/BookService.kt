package com.example.an_googlebooks

import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class BookService {

    companion object {
        fun getBooks(search: String, callback: ((List<Book>) -> Unit)) {
            val url = BookApi(search).getUrl
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object: okhttp3.Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body()?.string()
                    val gson = GsonBuilder().create()
                    body?.let {
                        val bookResponse = gson.fromJson(it, BookResponse::class.java)
                        callback(bookResponse.books)
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    println("TAG: Failed To Get Books")
                }
            })
        }

    }



}