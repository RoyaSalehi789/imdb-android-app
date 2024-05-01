package com.example.eslamshahr.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteList(
    @PrimaryKey(autoGenerate = true)
    val favoriteList_id: Int,
    val favoriteList_name: String,
    val username: String,
)
