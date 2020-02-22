package com.example.an_googlebooks

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class BookResponse(@SerializedName("items") val books: List<Book>)

data class Book(@SerializedName("volumeInfo") val info: VolumeInfo, val id: String): Serializable

data class VolumeInfo(val title: String, @SerializedName("imageLinks") val image: Image?, val authors: List<String>, val description: String): Serializable

data class Image(val thumbnail: String):Serializable