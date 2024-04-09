package com.example.kitap.di

import com.example.kitap.api.ApiInterface
import com.example.kitap.api.BookRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return  FirebaseAuth.getInstance()
    }
    @Singleton
    @Provides
    fun provideBookRepository(apiInterface: ApiInterface): BookRepository {
        return BookRepository(apiInterface)
    }
}