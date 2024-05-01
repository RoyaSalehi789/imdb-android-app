package com.example.eslamshahr.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.eslamshahr.entities.MovieEntity
import com.example.eslamshahr.entities.FavoriteList
import com.example.eslamshahr.entities.WatchList

data class MovieWithWatchLists(
    @Embedded val movieEntity: MovieEntity,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "watchList_user",
        associateBy = Junction(MovieWatchListCrossRef::class)
    )
    val watchList: List<WatchList>
)