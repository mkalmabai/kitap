package com.example.kitap.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitap.api.BookRepository
import com.example.kitap.model.BookResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    private val _booksResponse = MutableLiveData<BookResponse>()
    val booksResponse: LiveData<BookResponse> = _booksResponse

    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchBooks(query)
                if (response.isSuccessful) {
                    _booksResponse.value = response.body()
                } else {
                    // Handle unsuccessful response
                    println("error")
                }
            } catch (e: Exception) {
                // Handle exception
                println(e.message)
            }
        }
    }
}