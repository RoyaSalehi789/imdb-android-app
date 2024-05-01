package com.example.eslamshahr.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eslamshahr.repositories.MovieRepository

class MakeWatchlistViewModelFactory
    (private val repository: MovieRepository,
     private val application: Application) : ViewModelProvider.Factory {
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MakeWatchlistViewModel::class.java)) {
            return MakeWatchlistViewModel(repository , application) as T
        }
        throw java.lang.IllegalArgumentException("Unknown view model class")
    }
}