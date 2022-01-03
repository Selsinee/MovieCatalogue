package com.example.moviecatalogue.data

import java.io.Serializable

/**
 * Created by Seline on 21/12/2021 17:27
 */
data class Film (
    var filmID: String,
    var title: String,
    var description: String,
    var rating: String,
    var genre: String,
    var status: String,
    var releaseDate: String,
    var image: Int
    ) : Serializable