package com.example.moviecatalogue.dagger

import android.content.Context
import com.example.moviecatalogue.data.source.FilmRepository
import com.example.moviecatalogue.data.source.local.FilmRoomDatabase
import com.example.moviecatalogue.data.source.remote.NetworkConfig
import com.example.moviecatalogue.data.source.remote.RemoteDataSource
import com.example.moviecatalogue.utils.AppExecutors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Seline on 24/01/2022 15:41
 */
@Module
class NetworkModule(val context: Context) {

    @Provides
    @Singleton
    fun provideNetworkConfig(): NetworkConfig = NetworkConfig()

    @Provides
    @Singleton
    fun provideRepository(): FilmRepository {

        val networkConfig = NetworkConfig()
        val filmDatabase = FilmRoomDatabase.getDatabase(context)
        val dao = filmDatabase.filmDao()
        val appExecutors = AppExecutors()

        return FilmRepository.getInstance(networkConfig, dao, appExecutors)
    }
}