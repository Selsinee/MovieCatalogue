package com.example.moviecatalogue.data.source.remote.response.film

import com.example.moviecatalogue.data.source.remote.response.genre.GenreResponse
import com.google.gson.annotations.SerializedName

/**
 * Created by Seline on 06/01/2022 10:15
 */
data class FilmResponse (
    @SerializedName("id")
    var filmID: Int = 0,

    @SerializedName("original_title")
    var title: String = "-",

    @SerializedName("name")
    var name: String = "-",

    @SerializedName("overview")
    var description: String = "No Description",

    @SerializedName("vote_average")
    var rating: Double = 0.0,

    @SerializedName("genre_ids")
    var genre: List<Int>,

    @SerializedName("genres")
    var genreName: List<GenreResponse>,

    @SerializedName("status")
    var status: String = "Unknown",

    @SerializedName("release_date")
    var releaseDate: String = "No date specified",

    @SerializedName("first_air_date")
    var firstAirDate: String = "No date specified",

    @SerializedName("poster_path")
    var image: String = ""
)