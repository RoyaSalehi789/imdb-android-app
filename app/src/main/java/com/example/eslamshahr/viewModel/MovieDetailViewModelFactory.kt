package com.example.eslamshahr.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eslamshahr.repositories.MovieRepository


class MovieDetailViewModelFactory (
    private val repository: MovieRepository,
    private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("Unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
                return MovieDetailViewModel(repository, application) as T
            }
            throw java.lang.IllegalArgumentException("Unknown View Model Class")
        }
}