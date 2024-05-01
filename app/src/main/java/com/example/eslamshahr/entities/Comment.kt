package com.example.eslamshahr.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val comment_id: Int,
    val context: String,
    val username: String,
    val movieId: Int,
)