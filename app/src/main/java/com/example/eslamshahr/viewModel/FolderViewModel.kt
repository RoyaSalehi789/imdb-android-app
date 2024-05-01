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

class FolderViewModel(private val repository: MovieRepository,
                      application: Application) : AndroidViewModel(application), Observable{


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var foldersDetailLiveData = MutableLiveData<List<MovieEntity>>()
    init {
        uiScope.launch {
            foldersDetailLiveData.postValue(repository.getMoviesOfFavoriteList(Storage.getInstance().favoriteListId).movieEntities)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}