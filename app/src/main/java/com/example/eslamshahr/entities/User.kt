package com.example.eslamshahr.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val username: String,
    val name: String,
    val phoneNumber: Long,
    val email: String,
    val password: String,
)
