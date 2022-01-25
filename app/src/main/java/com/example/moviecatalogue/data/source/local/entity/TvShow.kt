package com.example.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by Seline on 24/01/2022 18:29
 */
@Entity(tableName = "TvShows")
@Parcelize
data class TvShow (
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