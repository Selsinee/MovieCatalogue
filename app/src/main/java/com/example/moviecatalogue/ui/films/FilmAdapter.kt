package com.example.moviecatalogue.ui.films

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.databinding.ItemFilmBinding
import com.example.moviecatalogue.ui.details.DetailFilmActivity

/**
 * Created by Seline on 21/12/2021 19:42
 */
class FilmAdapter : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    private var listFilms = ArrayList<Film>()

    fun setFilms(films: List<Film>?) {
        if (films == null) return
        this.listFilms.clear()
        this.listFilms.addAll(films)
    }

    class FilmViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            with(binding) {
                textTitle.text = film.title
                textGenre.text =
                    itemView.context.getString(R.string.text_genre_template, film.genre)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFilmActivity::class.java)
                    intent.putExtra(DetailFilmActivity.EXTRA_FILM, film)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(AppCompatResources.getDrawable(itemView.context, film.image))
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
        holder.bind(film)
    }

    override fun getItemCount(): Int = listFilms.size
}