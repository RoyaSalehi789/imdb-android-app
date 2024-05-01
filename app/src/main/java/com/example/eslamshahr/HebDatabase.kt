package com.example.eslamshahr

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eslamshahr.entities.*
import com.example.eslamshahr.entities.relations.MovieFavoriteListCrossRef
import com.example.eslamshahr.entities.relations.MovieWatchListCrossRef

@Database(
    entities = [
        Comment::class,
        MovieEntity::class,
        User::class,
        FavoriteList::class,
        WatchList::class,
        MovieFavoriteListCrossRef::class,
        MovieWatchListCrossRef::class,
    ],
    version = 2
)
abstract class HebDatabase : RoomDatabase() {
    abstract val iDao: IDao

    companion object{
        @Volatile
        private var INSTANCE: HebDatabase? = null

        fun getInstance(context: Context): HebDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    HebDatabase::class.java,
                    "HebMovie_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}