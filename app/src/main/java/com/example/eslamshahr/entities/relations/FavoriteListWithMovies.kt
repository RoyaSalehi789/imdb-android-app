package com.example.eslamshahr.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.eslamshahr.entities.MovieEntity
import com.example.eslamshahr.entities.FavoriteList

data class FavoriteListWithMovies(
    @Embedded val favoriteList: FavoriteList,
    @Relation(
        parentColumn = "favoriteList_id",
        entityColumn = "movie_id",
        associateBy = Junction(MovieFavoriteListCrossRef::class)
    )
    val movieEntities: List<MovieEntity>
)