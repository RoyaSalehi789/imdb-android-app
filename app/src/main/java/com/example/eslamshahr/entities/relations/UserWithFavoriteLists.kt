package com.example.eslamshahr.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.eslamshahr.entities.User
import com.example.eslamshahr.entities.FavoriteList

data class UserWithFavoriteLists(
    @Embedded val user: User,
    @Relation(
        parentColumn = "username",
        entityColumn = "username"
    )
    val favoriteLists: List<FavoriteList>
)