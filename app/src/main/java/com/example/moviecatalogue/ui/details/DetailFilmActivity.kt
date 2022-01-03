package com.example.moviecatalogue.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.databinding.ActivityDetailFilmBinding

class DetailFilmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val film = intent.getSerializableExtra(EXTRA_FILM)
        if (film != null) setFilmDetails(film as Film)
    }

    private fun setFilmDetails(film: Film) {
        with(binding) {
            imgPoster.setImageResource(film.image)
            imgPoster.setEdgeLength(200)
            imgPoster.setFadeDirection(FadingImageView.FadeSide.BOTTOM_SIDE)
            tvTitle.text = film.title
            tvDesc.text = film.description
            tvGenre.text = getString(R.string.text_genre_template, film.genre)
            tvRelease.text = getString(R.string.text_release_template, film.releaseDate)
            tvStatus.text = getString(R.string.text_status_template, film.status)
            progBar.progress = film.rating.toInt()
            progText.text = film.rating
        }

    }

    companion object {
        const val EXTRA_FILM = "extra_film"
    }
}