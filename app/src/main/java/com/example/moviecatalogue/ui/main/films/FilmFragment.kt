package com.example.moviecatalogue.ui.main.films

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.databinding.FragmentFilmBinding
import com.example.moviecatalogue.ui.details.DetailFilmActivity
import com.example.moviecatalogue.ui.main.FilmListViewModel
import com.example.moviecatalogue.ui.viewmodel.ViewModelFactory

class FilmFragment : Fragment() {

    private var type: String? = null
    private lateinit var fragmentFilmBinding: FragmentFilmBinding
    private lateinit var viewModel: FilmListViewModel
    private lateinit var filmAdapter: FilmAdapter

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
            initViewModel()
            initAdapter()

            viewModel.setIsLoading(true)

            if (type == "tvshow") {
                viewModel.getTvShowGenreList().observe(viewLifecycleOwner, {
                    viewModel.getTvShows(it).observe(viewLifecycleOwner, { listFilm ->
                        filmAdapter.setFilms(listFilm)
                        viewModel.setIsLoading(false)
                    })
                })

            } else viewModel.getMovieGenreList().observe(viewLifecycleOwner, {
                viewModel.getMovies(it).observe(viewLifecycleOwner, { listFilm ->
                    filmAdapter.setFilms(listFilm)
                    viewModel.setIsLoading(false)
                })
            })

            viewModel.isLoading.observe(viewLifecycleOwner, {
                toggleLoadingVisibility(it)
            })

        }

    }

    private fun toggleLoadingVisibility(value: Boolean) {
        fragmentFilmBinding.progBar.visibility = if (value) View.VISIBLE else View.GONE
    }

    private fun initAdapter() {
        filmAdapter = FilmAdapter { id ->
            val intent = Intent(context, DetailFilmActivity::class.java)
            intent.putExtra(DetailFilmActivity.FILM_ID, id)
            intent.putExtra(DetailFilmActivity.FILM_TYPE, type)
            requireContext().startActivity(intent)
        }

        with(fragmentFilmBinding.rvFilm) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            adapter = filmAdapter
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(
            this,
            factory
        )[FilmListViewModel::class.java]
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