package com.example.eslamshahr.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    var movie_id: Int,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val rating: Float,
    val count: Int,
    val releaseDate: String?
)