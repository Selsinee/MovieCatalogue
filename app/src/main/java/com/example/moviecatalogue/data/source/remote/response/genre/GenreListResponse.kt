package com.example.moviecatalogue.data.source.remote.response.genre

import com.google.gson.annotations.SerializedName

/**
 * Created by Seline on 06/01/2022 10:15
 */
data class GenreListResponse (
    @SerializedName("genres")
    var genres: List<GenreResponse>
    )