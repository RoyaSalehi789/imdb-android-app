package com.example.eslamshahr.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.eslamshahr.entities.FavoriteList
import com.example.eslamshahr.entities.MovieEntity
import com.example.eslamshahr.entities.WatchList

data class WatchListWithMovies (
    @Embedded val watchList: WatchList,
    @Relation(
        parentColumn = "watchList_user",
        entityColumn = "movie_id",
        associateBy = Junction(MovieWatchListCrossRef::class)
    )
    val movieEntities: List<MovieEntity>
)