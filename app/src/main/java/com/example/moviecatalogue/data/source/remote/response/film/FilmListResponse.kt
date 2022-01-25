package com.example.moviecatalogue.data.source.remote.response.film

import com.google.gson.annotations.SerializedName

/**
 * Created by Seline on 06/01/2022 10:15
 */
data class FilmListResponse (
    @SerializedName("results")
    var filmList : List<FilmResponse>
    )