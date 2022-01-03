package com.example.moviecatalogue.ui.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.databinding.FragmentFilmBinding
import com.example.moviecatalogue.ui.main.FilmListViewModel

class FilmFragment : Fragment() {

    private var type: String? = null
    private lateinit var fragmentFilmBinding: FragmentFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(ARG_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentFilmBinding = FragmentFilmBinding.inflate(inflater, container, false)
        return fragmentFilmBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val viewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[FilmListViewModel::class.java]
            val films =
                if (type == "movie") viewModel.getMovies() else viewModel.getTvShows()
            val filmAdapter = FilmAdapter()
            filmAdapter.setFilms(films)
            with(fragmentFilmBinding.rvFilm) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = filmAdapter
            }
        }

    }

    companion object {
        private const val ARG_TYPE = "movie"
        const val TYPE_MOVIE = "movie"
        const val TYPE_TVSHOW = "tvshow"

        @JvmStatic
        fun newInstance(type: String) =
            FilmFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPE, type)
                }
            }
    }
}