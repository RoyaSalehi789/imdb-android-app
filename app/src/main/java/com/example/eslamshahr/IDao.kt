package com.example.eslamshahr

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.eslamshahr.entities.*
import com.example.eslamshahr.entities.relations.*

@Dao
interface IDao {
    @Insert
    suspend fun insertComment(comment: Comment)

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Update
    suspend fun updateMovie(movieEntity: MovieEntity)

    @Insert
    suspend fun insertUser(user: User)

    @Insert
    suspend fun insertFavoriteList(favoriteList: FavoriteList)

    @Insert
    suspend fun insertWatchList(watchList: WatchList)

    @Insert
    suspend fun insertMovieWatchListCrossRef(crossRef:MovieWatchListCrossRef)

    @Insert
    suspend fun insertMovieFavoriteListCrossRef(crossRef:MovieFavoriteListCrossRef)

    @Query("SELECT * FROM user ORDER BY username DESC ")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE username = :username ")
    suspend fun getUsername(username: String): User?

    @Query("SELECT * FROM MovieEntity WHERE  movie_id = :movie_id ")
    suspend fun getMovie(movie_id: Int): MovieEntity

    @Transaction
    @Query("SELECT * FROM movieentity WHERE movie_id = :movie_id ")
    suspend fun getMovieWithComments(movie_id: Int): MovieWithComments

    @Query("SELECT * FROM favoritelist ORDER BY favoriteList_id DESC LIMIT 1 ")
    suspend fun getFavoriteList(): FavoriteList

    @Transaction
    @Query("SELECT * FROM user WHERE username = :username ")
    suspend fun getUserWithFavoriteLists(username: String): UserWithFavoriteLists

    @Transaction
    @Query("SELECT * FROM favoriteList WHERE favoriteList_id = :favoriteList_id ")
    suspend fun getMoviesOfFavoriteList(favoriteList_id: Int): FavoriteListWithMovies

    @Transaction
    @Query("SELECT * FROM watchlist WHERE watchList_user = :watchList_user ")
    suspend fun getMoviesOfWatchList(watchList_user: String): WatchListWithMovies

    @Transaction
    @Query("SELECT * FROM movieentity WHERE movie_id = :movie_id ")
    suspend fun getFavoriteListOfMovie(movie_id: Int): MovieWithFavoriteLists
}