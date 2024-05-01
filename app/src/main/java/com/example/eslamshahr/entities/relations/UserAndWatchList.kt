package com.example.eslamshahr.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.eslamshahr.entities.User
import com.example.eslamshahr.entities.WatchList

data class UserAndWatchList (
    @Embedded val user: User,
    @Relation(
        parentColumn = "username",
        entityColumn = "watchList_user"
    )
    val watchList: WatchList
        )