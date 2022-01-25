package com.example.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.data.source.remote.response.film.FilmListResponse
import com.example.moviecatalogue.data.source.remote.response.film.FilmResponse
import com.example.moviecatalogue.data.source.remote.response.genre.GenreListResponse
import com.example.moviecatalogue.data.source.remote.response.genre.GenreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Seline on 22/01/2022 15:18
 */
class RemoteDataSource {

    private val IMAGE_API = "https://image.tmdb.org/t/p/w500"
    private val networkConfig = NetworkConfig()

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    fun getMovies(genreList: List<GenreResponse>): LiveData<List<Film>> {
        val filmList = MutableLiveData<List<Film>>()
        networkConfig
            .getService()
            .getMovies()
            .enqueue(object : Callback<FilmListResponse> {
                override fun onResponse(
                    call: Call<FilmListResponse>,
                    response: Response<FilmListResponse>
                ) {
                    val movieList: ArrayList<Film> = ArrayList()
                    if (response.isSuccessful) {
                        response.body()?.filmList?.let {
                            for (item in it) {
                                val film = Film(
                                    item.filmID.toString(),
                                    item.title,
                                    "",
                                    "",
                                    getGenreString(item.genre, genreList),
                                    "",
                                    item.releaseDate,
                                    IMAGE_API + item.image
                                )
                                movieList.add(film)
                            }
                        }

                    }
                    filmList.postValue(movieList)
                }

                override fun onFailure(call: Call<FilmListResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        return filmList
    }

    fun getMovieGenres(): LiveData<List<GenreResponse>> {
        val genres = MutableLiveData<List<GenreResponse>>()
        networkConfig
            .getService()
            .getMovieGenres()
            .enqueue(object : Callback<GenreListResponse> {
                override fun onResponse(
                    call: Call<GenreListResponse>,
                    response: Response<GenreListResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.genres?.let {
                            genres.postValue(it)
                        }
                    }
                }

                override fun onFailure(call: Call<GenreListResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        return genres
    }

    fun getTvShowGenres(): LiveData<List<GenreResponse>> {
        val genres = MutableLiveData<List<GenreResponse>>()
        networkConfig
            .getService()
            .getTvShowGenres()
            .enqueue(object : Callback<GenreListResponse> {
                override fun onResponse(
                    call: Call<GenreListResponse>,
                    response: Response<GenreListResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.genres?.let {
                            genres.postValue(it)
                        }
                    }
                }

                override fun onFailure(call: Call<GenreListResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        return genres
    }

    fun getGenreString(genreIDs: List<Int>?, genreList: List<GenreResponse>): String {
        var genre = ""
        if (genreIDs != null) {
            for (id in genreIDs) {
                for (item in genreList) {
                    if (item.id == id) {
                        if (genre != "") genre += ", "
                        genre += item.name
                    }
                }
            }
        }
        if (genre == "") genre = "No genre"
        return genre
    }

    fun getTvShows(genreList: List<GenreResponse>): LiveData<List<Film>> {
        val filmList = MutableLiveData<List<Film>>()
        networkConfig
            .getService()
            .getTvShows()
            .enqueue(object : Callback<FilmListResponse> {
                override fun onResponse(
                    call: Call<FilmListResponse>,
                    response: Response<FilmListResponse>
                ) {
                    val tvList: ArrayList<Film> = ArrayList()
                    if (response.isSuccessful) {
                        response.body()?.filmList?.let {
                            for (item in it) {
                                val film = Film(
                                    item.filmID.toString(),
                                    item.name,
                                    "",
                                    "",
                                    getGenreString(item.genre, genreList),
                                    "",
                                    item.firstAirDate,
                                    IMAGE_API + item.image
                                )
                                tvList.add(film)
                            }
                        }
                    }
                    filmList.postValue(tvList)
                }

                override fun onFailure(call: Call<FilmListResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        return filmList
    }

    fun getGenreStringDetails(genreList: List<GenreResponse>): String {
        var genre = ""
        for (item in genreList) {
            if (genre != "") genre += ", "
            genre += item.name
        }

        if (genre == "") genre = "No genre"
        return genre
    }

    fun getMovieDetails(id: String): LiveData<Film> {
        val film = MutableLiveData<Film>()
        networkConfig
            .getService()
            .getMovieDetails(id)
            .enqueue(object : Callback<FilmResponse> {
                override fun onResponse(
                    call: Call<FilmResponse>,
                    response: Response<FilmResponse>
                ) {
                    var res : Film? = null
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d("<TAG>", it.toString())
                            res = Film(
                                it.filmID.toString(),
                                it.title,
                                it.description,
                                (it.rating * 10).toInt().toString(),
                                getGenreStringDetails(it.genreName),
                                it.status,
                                it.releaseDate,
                                IMAGE_API + it.image
                            )
                        }
                    }
                    Log.d("<TAG>", res.toString())
                    film.postValue(res)
                }

                override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        return film
    }

    fun getTvShowDetails(id: String): LiveData<Film> {
        val film = MutableLiveData<Film>()
        networkConfig
            .getService()
            .getTvShowDetails(id)
            .enqueue(object : Callback<FilmResponse> {
                override fun onResponse(
                    call: Call<FilmResponse>,
                    response: Response<FilmResponse>
                ) {
                    var res : Film? = null
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d("<TAG>", it.toString())
                            res = Film(
                                it.filmID.toString(),
                                it.name,
                                it.description,
                                (it.rating * 10).toInt().toString(),
                                getGenreStringDetails(it.genreName),
                                it.status,
                                it.firstAirDate,
                                IMAGE_API + it.image
                            )
                        }
                    }
                    film.postValue(res)
                }

                override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        return film
    }

}