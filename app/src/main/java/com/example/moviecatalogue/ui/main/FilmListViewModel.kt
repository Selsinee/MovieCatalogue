package com.example.moviecatalogue.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.data.source.FilmRepository
import com.example.moviecatalogue.data.source.remote.response.genre.GenreResponse

/**
 * Created by Seline on 31/12/2021 17:33
 */
class FilmListViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun getMovieGenreList(): LiveData<List<GenreResponse>> = filmRepository.getMovieGenres()

    fun getTvShowGenreList(): LiveData<List<GenreResponse>> = filmRepository.getTvShowGenres()

    fun getTvShows(genreList: List<GenreResponse>) : LiveData<List<Film>> = filmRepository.getTvShows(genreList)

    fun getMovies(genreList: List<GenreResponse>): LiveData<List<Film>> = filmRepository.getMovies(genreList)


}