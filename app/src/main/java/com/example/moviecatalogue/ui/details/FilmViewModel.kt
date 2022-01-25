package com.example.moviecatalogue.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.data.source.FilmRepository

/**
 * Created by Seline on 22/01/2022 17:17
 */
class FilmViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun getMovieDetails(id : String) : LiveData<Film> = filmRepository.getMovieDetails(id)
    fun getTvShowDetails(id : String) : LiveData<Film> = filmRepository.getTvShowDetails(id)
}