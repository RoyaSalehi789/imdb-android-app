package com.example.eslamshahr.movieApi


import android.graphics.Region
import com.example.eslamshahr.model.Credits
import com.example.eslamshahr.model.Actor
import com.example.eslamshahr.response.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    //popularMovies
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

   //topRatedMovies
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    //upComingMovies
    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    //search for movies
    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
        @Query("page") page: Int,
        @Query("query") query: String
    ): Call<GetMoviesResponse>

    @GET("movie/{movie_id}/credits")
    fun getCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
    ): Call<Credits>

    @GET("movie/{movie_id}")
    fun getDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
    ): Call<Credits>

    @GET("discover/movie")
    fun horrorMovie(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
        @Query("page") page: Int,
        @Query("with_genres") genres: Int
    ): Call<GetMoviesResponse>

    @GET("discover/movie")
    fun dramaMovie(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
        @Query("page") page: Int,
        @Query("with_genres") genres: Int
    ): Call<GetMoviesResponse>

    @GET("discover/movie")
    fun animationMovie(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
        @Query("page") page: Int,
        @Query("with_genres") genres: Int
    ): Call<GetMoviesResponse>

    @GET("search/person")
    fun actorSearch(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
        @Query("page") page: Int,
        @Query("query") query: String
    ): Call<Actor>

    @GET("person/popular")
    fun popularPerson(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
        @Query("page") page: Int,
    ): Call<Actor>

    @GET("person/latest")
    fun latestPerson(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
    ): Call<Actor>

    @GET("discover/movie")
    fun filterMovie(
        @Query("api_key") apiKey: String = "9ca5337fbeebde0c0d6ba3329adecc61",
        @Query("region") region: String,
        @Query("page") page: Int,
        @Query("year") year: Int,
        @Query("with_genres") genres: String
    ): Call<GetMoviesResponse>



}