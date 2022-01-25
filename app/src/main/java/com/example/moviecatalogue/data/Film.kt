package com.example.moviecatalogue.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * Created by Seline on 21/12/2021 17:27
 */

@Parcelize
data class Film (
    @PrimaryKey
    @ColumnInfo(name = "filmID")
    var filmID: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "rating")
    var rating: String,

    @ColumnInfo(name = "genre")
    var genre: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String,

    @ColumnInfo(name = "image")
    var image: String
    ) : Parcelable