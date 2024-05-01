package com.example.eslamshahr.viewModel

import android.app.Application
import android.widget.Toast
import androidx.databinding.Observable
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eslamshahr.entities.Comment
import com.example.eslamshahr.entities.FavoriteList
import com.example.eslamshahr.entities.MovieEntity
import com.example.eslamshahr.entities.relations.MovieFavoriteListCrossRef
import com.example.eslamshahr.model.Storage
import com.example.eslamshahr.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MakeWatchlistViewModel(private val repository: MovieRepository,
                             application: Application) : AndroidViewModel(application), Observable{


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _errorToast = MutableLiveData<Boolean>()

    val errorToast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastSelect = MutableLiveData<Boolean>()

    val errorToastSelect: LiveData<Boolean>
        get() = _errorToastSelect

    fun createButton(name: String) {
        uiScope.launch {
            if (name == null) {
               _errorToast.value = true
            }
            else {
                if (Storage.getInstance().movies.size == 0) {
                    _errorToastSelect.value = true
                }
                else {
                    for (index in 0 until Storage.getInstance().movies.size) {
                        insertMovie(MovieEntity(Storage.getInstance().movies[index].id,
                            Storage.getInstance().movies[index].title,
                            Storage.getInstance().movies[index].overview,
                            Storage.getInstance().movies[index].posterPath,
                            Storage.getInstance().movies[index].backdropPath,
                            Storage.getInstance().movies[index].rating,
                            Storage.getInstance().movies[index].count,
                            Storage.getInstance().movies[index].releaseDate))
                    }

                    var favoriteList = FavoriteList(0, name, Storage.getInstance().username)
                    insertFavoriteList(favoriteList)
                    favoriteList = repository.getFavoriteList()

                    for (index in 0 until Storage.getInstance().movies.size ) {
                        insertMovieFavoriteListCrossRef(MovieFavoriteListCrossRef(Storage.getInstance().movies[index].id,
                        favoriteList.favoriteList_id))
                    }

                    Storage.getInstance().movies.clear()
                }
            }
        }
    }

    fun donetoast() {
        _errorToast.value = false
    }

    fun donetoastSelect() {
        _errorToastSelect.value = false
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    private fun insertMovie(movieEntity: MovieEntity) : Job = viewModelScope.launch{
        val movie = repository.getMovie(movieEntity.movie_id!!)
        if (movie == null) repository.insertMovie(movieEntity)
    }

    private fun insertFavoriteList(favoriteList: FavoriteList) : Job = viewModelScope.launch {
        repository.insertFavoriteList(favoriteList)
    }

    private fun insertMovieFavoriteListCrossRef(crossRef: MovieFavoriteListCrossRef) : Job = viewModelScope.launch {
        repository.insertMovieFavoriteListCrossRef(crossRef)
    }

}