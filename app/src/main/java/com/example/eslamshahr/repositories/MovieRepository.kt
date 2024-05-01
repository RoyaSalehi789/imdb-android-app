package com.example.eslamshahr.repositories

import com.example.eslamshahr.IDao
import com.example.eslamshahr.entities.*
import com.example.eslamshahr.entities.relations.*

class MovieRepository(private val dao: IDao) {

    suspend fun insert(comment: Comment) {
        return dao.insertComment(comment)
    }

    suspend fun insertMovie(movieEntity: MovieEntity) {
        return dao.insertMovie(movieEntity)
    }

    suspend fun updateMovie(movieEntity: MovieEntity) {
        return dao.updateMovie(movieEntity)
    }


    suspend fun insertFavoriteList(favoriteList: FavoriteList) {
        return dao.insertFavoriteList(favoriteList)
    }

    suspend fun insertMovieWatchListCrossRef(crossRef: MovieWatchListCrossRef) {
        return dao.insertMovieWatchListCrossRef(crossRef)
    }

    suspend fun insertWatchList(watchList: WatchList) {
        return dao.insertWatchList(watchList)
    }

    suspend fun insertMovieFavoriteListCrossRef(crossRef: MovieFavoriteListCrossRef) {
        return dao.insertMovieFavoriteListCrossRef(crossRef)
    }

    suspend fun getUserWithFavoriteLists(username: String) : UserWithFavoriteLists {
        return dao.getUserWithFavoriteLists(username)
    }

    suspend fun getFavoriteList(): FavoriteList {
        return dao.getFavoriteList()
    }

    suspend fun getFavoriteListOfMovie(movie_id: Int): MovieWithFavoriteLists {
        return dao.getFavoriteListOfMovie(movie_id)
    }

    suspend fun getMoviesOfFavoriteList(favoriteList_id: Int): FavoriteListWithMovies {
        return dao.getMoviesOfFavoriteList(favoriteList_id)
    }

    suspend fun getMoviesOfWatchList(watchList_user: String): WatchListWithMovies {
        return dao.getMoviesOfWatchList(watchList_user)
    }

    suspend fun getMovieWithComments(movie_id: Int) : MovieWithComments {
        return dao.getMovieWithComments(movie_id)
    }

    suspend fun getMovie(movie_id: Int) : MovieEntity {
        return dao.getMovie(movie_id)
    }
}