package com.emonics.bookstoreapp

data class BookModel (
// Creating variables for our bookStore kotlin project
    val title: String,
    val author: String,
    val isbn: Int,
    val image: String,
    val price: Int,
    val description: String,
    val preview: String,
    val publisher: String,
    val publishedDate: String,
    val pageCount: Int,
    val thumbnail: String,
    var previewLink: String,
    var infoLink: String,
    var buyLink: String
)