package com.emonics.bookstoreapp

data class BookModel (
//Creating variables for our bookStore kotlin project
    val title: String,
    val author: String,
    val isbn: Int,
    val image: String,
    val price: Int,
    val description: String,
    val preview: String,
    var publisher: String,
    var publishedDate: String,
    var pageCount: Int,
    var thumbnail: String,
    var previewLink: String,
    var infoLink: String,
    var buyLink: String
)