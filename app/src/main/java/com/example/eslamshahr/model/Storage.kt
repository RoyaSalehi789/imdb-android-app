package com.example.eslamshahr.model

class Storage {

    var movieId: Int = 0
    var rate: Float = 0f
    var favoriteListId = 0
    var username: String = ""
    var movies: MutableList<Movie> = arrayListOf()

    companion object {
        @Volatile
        private lateinit var instance: Storage

        fun getInstance(): Storage {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = Storage()
                }
                return  instance
            }
        }
    }
}