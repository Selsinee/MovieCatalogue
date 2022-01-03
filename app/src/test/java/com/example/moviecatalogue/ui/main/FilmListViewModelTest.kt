package com.example.moviecatalogue.ui.main

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Created by Seline on 31/12/2021 17:38
 */

class FilmListViewModelTest {

    private lateinit var filmListViewModel: FilmListViewModel

    @Before
    fun setUp() {
        filmListViewModel = FilmListViewModel()
    }

    @Test
    fun getTvShows() {
        val tvShows = filmListViewModel.getTvShows()
        assertNotNull(tvShows)
        assertEquals(20, tvShows.size)
    }

    @Test
    fun getMovies() {
        val movies = filmListViewModel.getMovies()
        assertNotNull(movies)
        assertEquals(18, movies.size)
    }
}