package com.example.moviecatalogue.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalogue.data.source.FilmRepository
import com.example.moviecatalogue.ui.details.FilmViewModel
import com.example.moviecatalogue.ui.di.Injection
import com.example.moviecatalogue.ui.main.FilmListViewModel

/**
 * Created by Seline on 22/01/2022 17:26
 */
class ViewModelFactory private constructor(private val filmRepository: FilmRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FilmListViewModel::class.java) -> {
                FilmListViewModel(filmRepository) as T
            }
            modelClass.isAssignableFrom(FilmViewModel::class.java) -> {
                FilmViewModel(filmRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}