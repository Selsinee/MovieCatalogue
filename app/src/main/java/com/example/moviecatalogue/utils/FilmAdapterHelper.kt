package com.example.moviecatalogue.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.moviecatalogue.data.Film

/**
 * Created by Seline on 23/01/2022 14:48
 */
class FilmAdapterHelper(
    private val oldList: List<Film>,
    private val newList: List<Film>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].filmID == newList[newItemPosition].filmID

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return compareContent(oldItem, newItem)
    }

    private fun compareContent(oldItem: Film, newItem: Film): Boolean {
        return (oldItem.title == newItem.title)
                && (oldItem.description == newItem.description)
                && (oldItem.genre == newItem.genre)
                && (oldItem.rating == newItem.rating)
                && (oldItem.releaseDate == newItem.releaseDate)
                && (oldItem.status == newItem.status)
                && (oldItem.image == newItem.image)

    }

}