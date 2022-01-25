package com.example.moviecatalogue.data.source.remote.`interface`

import com.example.moviecatalogue.data.source.remote.response.film.FilmListResponse
import com.example.moviecatalogue.data.source.remote.response.film.FilmResponse
import com.example.moviecatalogue.data.source.remote.response.genre.GenreListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Seline on 06/01/2022 10:57
 */
interface IFilm{

    @GET("movie/popular")
    fun getMovies(): Call<FilmListResponse>

    @GET("tv/popular")
    fun getTvShows(): Call<FilmListResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: String): Call<FilmResponse>

    @GET("tv/{id}")
    fun getTvShowDetails(@Path("id") id: String): Call<FilmResponse>

    @GET("genre/movie/list")
    fun getMovieGenres(): Call<GenreListResponse>

    @GET("genre/tv/list")
    fun getTvShowGenres(): Call<GenreListResponse>

}