package com.example.eslamshahr.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = ["movie_id", "watchList_user"])
data class MovieWatchListCrossRef(
    val movie_id: Int,
    val watchList_user: String,
)