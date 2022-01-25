package com.example.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.data.source.remote.response.genre.GenreResponse

/**
 * Created by Seline on 06/01/2022 11:04
 */
interface IFilmDataSource {
    fun getMovieGenres() : LiveData<List<GenreResponse>>
    fun getTvShowGenres() : LiveData<List<GenreResponse>>
    fun getMovies(genreList: List<GenreResponse>) : LiveData<List<Film>>
    fun getTvShows(genreList: List<GenreResponse>) : LiveData<List<Film>>
    fun getMovieDetails(id: String) : LiveData<Film>
    fun getTvShowDetails(id: String) : LiveData<Film>
}