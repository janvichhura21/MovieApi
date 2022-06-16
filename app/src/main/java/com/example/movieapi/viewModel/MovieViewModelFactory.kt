package com.example.movieapi.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapi.db.MovieDatabase

class MovieViewModelFactory(private val movieDatabase: MovieDatabase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailViewModel::class.java!!)) {
            DetailViewModel(movieDatabase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}