package com.emonics.bookstoreapp

data class BookModel(
// Creating variables for our bookStore kotlin project
    // creating a constructor class for our BookInfo
    // creating getter and setter methods
    // creating string, int and array list
    // variables for our book details
    var title: String,
    var subtitle: String,
    var authors: ArrayList<String>,
    var publisher: String,
    var publishedDate: String,
    var description: String,
    var pageCount: Int,
    var thumbnail: String,
    var previewLink: String,
    var infoLink: String,
    var buyLink: String
)