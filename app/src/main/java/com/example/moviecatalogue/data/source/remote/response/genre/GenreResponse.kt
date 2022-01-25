package com.example.moviecatalogue.data.source.remote.response.genre

import com.google.gson.annotations.SerializedName

/**
 * Created by Seline on 06/01/2022 10:26
 */
data class GenreResponse (
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String
)