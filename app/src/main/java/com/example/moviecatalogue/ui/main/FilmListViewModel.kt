package com.example.moviecatalogue.ui.main

import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.utils.DataDummy

/**
 * Created by Seline on 31/12/2021 17:33
 */
class FilmListViewModel : ViewModel() {

    fun getTvShows(): List<Film> = DataDummy.generateDummyTVShows()
    fun getMovies(): List<Film> = DataDummy.generateDummyMovies()

}