package com.example.eslamshahr.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("vote_count") val count: Int,
    @SerializedName("genre_ids") val genres: Array<Int>,
    @SerializedName("release_date") val releaseDate: String
)