package com.example.kitap.api

import com.example.kitap.model.BookResponse
import com.example.kitap.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("books/v1/volumes")
    suspend fun searchBooks(
        @Query("q")
        searchQuery: String ,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<BookResponse>
}