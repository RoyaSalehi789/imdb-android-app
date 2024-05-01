package com.example.eslamshahr.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class WatchList (
        @PrimaryKey(autoGenerate = true)
        val watchlist_id: Int,
        val watchList_user: String,
)