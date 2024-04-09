package com.example.kitap.model

data class BookResponse(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)