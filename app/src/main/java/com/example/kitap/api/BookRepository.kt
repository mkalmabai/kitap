package com.example.kitap.api

import com.example.kitap.model.BookResponse
import retrofit2.Response
import javax.inject.Inject

class BookRepository  @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun searchBooks(searchQuery: String): Response<BookResponse> {
        return apiInterface.searchBooks(searchQuery)
    }
}