package com.example.eslamshahr.model

import com.example.eslamshahr.entities.Comment
import com.example.eslamshahr.entities.relations.MovieWithComments

class CommentsStorage {

    var comments: List<Comment>? = null

    companion object {
        @Volatile
        private lateinit var instance: CommentsStorage

        fun getInstance(): CommentsStorage {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = CommentsStorage()
                }
                return  instance
            }
        }
    }
}