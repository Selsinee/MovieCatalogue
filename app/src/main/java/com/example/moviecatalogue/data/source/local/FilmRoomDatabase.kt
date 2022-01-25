package com.example.moviecatalogue.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.data.source.local.entity.Movie
import com.example.moviecatalogue.data.source.local.entity.TvShow

/**
 * Created by Seline on 24/01/2022 18:11
 */
@Database(entities = [Movie::class, TvShow::class], version = 1)
abstract class FilmRoomDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao
    companion object {
        @Volatile
        private var INSTANCE: FilmRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FilmRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FilmRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FilmRoomDatabase::class.java, "film_database")
                        .build()
                }
            }
            return INSTANCE as FilmRoomDatabase
        }
    }
}