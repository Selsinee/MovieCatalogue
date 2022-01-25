package com.example.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.data.source.local.entity.Movie
import com.example.moviecatalogue.data.source.local.entity.TvShow

/**
 * Created by Seline on 24/01/2022 18:09
 */
@Dao
interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Movie::class)
    fun insertMovies(movies: List<Film>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = TvShow::class)
    fun insertTvShows(tvshows: List<Film>) : List<Long>

    @Query("SELECT * from Movies ORDER BY filmId ASC")
    fun getAllMovies(): LiveData<List<Film>>

    @Query("SELECT * from TvShows ORDER BY filmId ASC")
    fun getAllTvShows(): LiveData<List<Film>>

    @Query("SELECT * from Movies WHERE filmID = :id")
    fun getMovieDetail(id: String): LiveData<Film>

    @Query("SELECT * from TvShows WHERE filmID = :id")
    fun getTvShowDetail(id: String): LiveData<Film>

    @Update(entity = TvShow::class)
    fun updateTvShowDetail(film: Film)

    @Update(entity = Movie::class)
    fun updateMovieDetail(film: Film)

    @Query("DELETE from Movies")
    fun deleteAllMovies()

    @Query("DELETE from TvShows")
    fun deleteAllTvShows()

}