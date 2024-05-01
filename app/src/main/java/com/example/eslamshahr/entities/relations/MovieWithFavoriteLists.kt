package com.example.eslamshahr.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.eslamshahr.entities.MovieEntity
import com.example.eslamshahr.entities.FavoriteList

data class MovieWithFavoriteLists(
    @Embedded val movieEntity: MovieEntity,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "favoriteList_id",
        associateBy = Junction(MovieFavoriteListCrossRef::class)
    )
    val favoriteList: List<FavoriteList>
)