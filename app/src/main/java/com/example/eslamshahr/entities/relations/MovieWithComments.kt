package com.example.eslamshahr.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.eslamshahr.entities.Comment
import com.example.eslamshahr.entities.MovieEntity

data class MovieWithComments(
    @Embedded val movieEntity: MovieEntity,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "movieId"
    )
    val comments: List<Comment>
)