package com.example.moviecatalogue.ui.di

import android.content.Context
import com.example.moviecatalogue.data.source.FilmRepository
import com.example.moviecatalogue.data.source.local.FilmDao
import com.example.moviecatalogue.data.source.local.FilmRoomDatabase
import com.example.moviecatalogue.data.source.remote.NetworkConfig
import com.example.moviecatalogue.data.source.remote.RemoteDataSource
import com.example.moviecatalogue.utils.AppExecutors

/**
 * Created by Seline on 22/01/2022 17:25
 */
object Injection {
    fun provideRepository(context: Context): FilmRepository {

        val remoteDataSource = RemoteDataSource.getInstance()
        val networkConfig = NetworkConfig()
        val filmDatabase = FilmRoomDatabase.getDatabase(context)
        val dao = filmDatabase.filmDao()
        val appExecutors = AppExecutors()


        return FilmRepository.getInstance(networkConfig, dao, appExecutors)
    }
}