package com.example.eslamshahr.viewModel

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eslamshahr.entities.Comment
import com.example.eslamshahr.entities.FavoriteList
import com.example.eslamshahr.entities.MovieEntity
import com.example.eslamshahr.entities.WatchList
import com.example.eslamshahr.model.Storage
import com.example.eslamshahr.repositories.MovieRepository
import com.example.eslamshahr.repositories.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: MovieRepository,
                       application: Application) : AndroidViewModel(application), Observable{
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var foldersLiveData = MutableLiveData<List<FavoriteList>>()
    init {
        uiScope.launch {
            foldersLiveData.postValue(repository.getUserWithFavoriteLists(Storage.getInstance().username).favoriteLists)
        }
    }

    var watchListLiveData = MutableLiveData<List<MovieEntity>>()
    init {
        uiScope.launch {
            var movie = repository.getMoviesOfWatchList(Storage.getInstance().username)
            if (movie != null)
            {
                watchListLiveData.postValue(movie.movieEntities)
            }

        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}