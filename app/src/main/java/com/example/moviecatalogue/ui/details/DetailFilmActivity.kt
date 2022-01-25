package com.example.moviecatalogue.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.databinding.ActivityDetailFilmBinding
import com.example.moviecatalogue.ui.main.films.FilmFragment
import com.example.moviecatalogue.ui.viewmodel.ViewModelFactory

class DetailFilmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFilmBinding
    private lateinit var viewModel: FilmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[FilmViewModel::class.java]

        val id = intent.getStringExtra(FILM_ID)
        val type = intent.getStringExtra(FILM_TYPE)
        if (id != null && type != null) getFilmDetails(id, type)

        viewModel.isLoading.observe(this, { toggleLoadingVisibility(it) })

    }

    override fun onResume() {
        super.onResume()
        viewModel.setIsLoading(true)
    }

    private fun getFilmDetails(id: String, type: String) {
            if (type ==
                FilmFragment.TYPE_TVSHOW
            ) viewModel.getTvShowDetails(id).observe(this, { film ->
                if (film != null) setFilmDetails(film)
            }) else viewModel.getMovieDetails(id).observe(this, { film ->
                if (film != null) setFilmDetails(film)
            })

    }

    private fun setFilmDetails(film: Film) {
        with(binding) {
            Glide.with(this@DetailFilmActivity)
                .load(film.image)
                .into(imgPoster)
            imgPoster.setEdgeLength(200)
            imgPoster.setFadeDirection(FadingImageView.FadeSide.BOTTOM_SIDE)
            tvTitle.text = film.title
            tvDesc.text = film.description
            tvGenre.text = getString(R.string.text_genre_template, film.genre)
            tvRelease.text = getString(R.string.text_release_template, film.releaseDate)
            tvStatus.text = getString(R.string.text_status_template, film.status)
            ratingBar.progress = film.rating.toInt()
            ratingText.text = film.rating
        }
        viewModel.setIsLoading(false)
    }

    private fun toggleLoadingVisibility(value: Boolean) {
        binding.progBar.visibility = if (value) View.VISIBLE else View.GONE
        binding.wrapper.visibility = if (!value) View.VISIBLE else View.GONE
    }

    companion object {
        const val FILM_ID = "film_id"
        const val FILM_TYPE = "film_type"
    }
}