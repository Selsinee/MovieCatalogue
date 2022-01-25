package com.example.moviecatalogue.ui.main.films

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.databinding.ItemFilmBinding
import com.example.moviecatalogue.utils.FilmAdapterHelper

/**
 * Created by Seline on 21/12/2021 19:42
 */
class FilmAdapter(private val onClick: (id: String) -> Unit) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    private var listFilms = ArrayList<Film>()

    fun setFilms(films: List<Film>?) {
        if (films == null) return
        val diffCallback = FilmAdapterHelper(this.listFilms, films)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFilms.clear()
        this.listFilms.addAll(films)
        diffResult.dispatchUpdatesTo(this)
    }

    class FilmViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film, onClick: (id: String) -> Unit) {
            with(binding) {
                textTitle.text = film.title
                textGenre.text =
                    itemView.context.getString(R.string.text_genre_template, film.genre)
                itemView.setOnClickListener {
                    onClick(film.filmID)
                }
                Glide.with(itemView.context)
                    .load(film.image)
                    .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val itemFilmBinding =
            ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder((itemFilmBinding))
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = listFilms[position]
        holder.bind(film, onClick)
    }

    override fun getItemCount(): Int = listFilms.size
}