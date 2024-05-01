package com.example.eslamshahr.viewModel

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eslamshahr.entities.Comment
import com.example.eslamshahr.entities.FavoriteList
import com.example.eslamshahr.entities.MovieEntity
import com.example.eslamshahr.entities.WatchList
import com.example.eslamshahr.entities.relations.MovieFavoriteListCrossRef
import com.example.eslamshahr.entities.relations.MovieWatchListCrossRef
import com.example.eslamshahr.model.Storage
import com.example.eslamshahr.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: MovieRepository,
                           application: Application
) : AndroidViewModel(application), Observable {
    @Bindable
    val inputComment = MutableLiveData<String?>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var commentsLiveData = MutableLiveData<List<Comment>>()
    init {
        uiScope.launch {
            val movie = repository.getMovie(Storage.getInstance().movieId)
            if (movie != null )commentsLiveData.postValue(repository.getMovieWithComments(Storage.getInstance().movieId).comments)
        }
    }

    var favoriteListLiveData = MutableLiveData<List<FavoriteList>>()


    init {
        uiScope.launch {
            val favoriteList = repository.getFavoriteListOfMovie(Storage.getInstance().movieId)
            if (favoriteList != null )favoriteListLiveData.postValue(favoriteList.favoriteList)
        }
    }
        fun sendButton(comment: String) {
            uiScope.launch {
                if (comment != null) {
                    insert(
                        Comment(
                            0,
                            comment,
                            Storage.getInstance().username,
                            Storage.getInstance().movieId
                        )
                    )
                }
            }
            inputComment.value = null
    }

    fun addToWatchList(movieEntity: MovieEntity) {
        uiScope.launch {
            insertMovie(movieEntity)
            var watchList = WatchList(0, Storage.getInstance().username)
            insertWatchList(watchList)
            insertMovieWatchListCrossRef(MovieWatchListCrossRef(movieEntity.movie_id, Storage.getInstance().username))
        }
    }

    fun updateRating(movieEntity: MovieEntity, rate: Float) {
        uiScope.launch {
            val movie = repository.getMovie(movieEntity.movie_id)
            val newRate = ((movie.rating * movie.count.toFloat()) + rate) / movie.count.toFloat() + 1
            repository.updateMovie(
                MovieEntity(movie.movie_id, movie.title, movie.overview,
            movie.posterPath, movie.backdropPath, newRate, movie.count + 1, movie.releaseDate)
            )
        }

    }

    fun getRate(movie_id: Int): Float
    {
        var rate: Float = 0f
        uiScope.launch {
            val movie = repository.getMovie(movie_id)
            if (movie!= null) rate = movie.rating
        }
        return rate
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    private fun insert(comment: Comment): Job = viewModelScope.launch {
        repository.insert(comment)
    }

    fun insertMovie(movieEntity: MovieEntity): Job = viewModelScope.launch {
        val movie = repository.getMovie(movieEntity.movie_id!!)
        if (movie == null) repository.insertMovie(movieEntity)
    }

    private fun insertWatchList(watchList: WatchList) : Job = viewModelScope.launch {
        repository.insertWatchList(watchList)
    }

    private fun insertMovieWatchListCrossRef(crossRef: MovieWatchListCrossRef) : Job = viewModelScope.launch {
        repository.insertMovieWatchListCrossRef(crossRef)
    }
}

