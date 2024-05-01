package com.example.eslamshahr.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = ["movie_id", "favoriteList_id"])
data class MovieFavoriteListCrossRef(
    val movie_id: Int,
    val favoriteList_id: Int,
)