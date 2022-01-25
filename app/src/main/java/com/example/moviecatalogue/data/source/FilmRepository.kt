package com.example.moviecatalogue.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviecatalogue.data.Film
import com.example.moviecatalogue.data.source.local.FilmDao
import com.example.moviecatalogue.data.source.remote.NetworkConfig
import com.example.moviecatalogue.data.source.remote.response.film.FilmListResponse
import com.example.moviecatalogue.data.source.remote.response.film.FilmResponse
import com.example.moviecatalogue.data.source.remote.response.genre.GenreListResponse
import com.example.moviecatalogue.data.source.remote.response.genre.GenreResponse
import com.example.moviecatalogue.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Seline on 06/01/2022 10:53
 */
class FilmRepository private constructor(
    private val networkConfig: NetworkConfig, private val filmsDao: FilmDao,
    private val appExecutors: AppExecutors
) : IFilmDataSource {

    private val IMAGE_API = "https://image.tmdb.org/t/p/w500"
    private val movieResult = MediatorLiveData<List<Film>>()
    private val tvshowResult = MediatorLiveData<List<Film>>()
    private val movieDetailResult = MediatorLiveData<Film>()
    private val tvshowDetailResult = MediatorLiveData<Film>()


    override fun getMovieGenres(): LiveData<List<GenreResponse>> {
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

    override fun getTvShowGenres(): LiveData<List<GenreResponse>> {
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

    override fun getMovies(genreList: List<GenreResponse>): LiveData<List<Film>> {
        val localData = filmsDao.getAllMovies()
        movieResult.addSource(localData) {
            if (!it.isNullOrEmpty()) {
                movieResult.removeSource(localData)
                movieResult.value = it
            } else {
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
                                appExecutors.diskIO.execute {
                                    response.body()?.filmList?.let { filmRes ->
                                        for (item in filmRes) {
                                            val film = Film(
                                                item.filmID.toString(),
                                                item.title,
                                                "",
                                                (item.rating * 10).toInt().toString(),
                                                getGenreString(item.genre, genreList),
                                                "",
                                                "",
                                                IMAGE_API + item.image
                                            )
                                            movieList.add(film)
                                        }
                                    }
                                    movieResult.postValue(movieList)
                                    filmsDao.deleteAllMovies()
                                    filmsDao.insertMovies(movieList)
                                }

                            }

                        }

                        override fun onFailure(call: Call<FilmListResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
            }
        }

        return movieResult

    }

    override fun getTvShows(genreList: List<GenreResponse>): LiveData<List<Film>> {
        val localData = filmsDao.getAllTvShows()
        tvshowResult.addSource(localData) {
            if (!it.isNullOrEmpty()) {
                tvshowResult.removeSource(localData)
                tvshowResult.value = it
            } else {
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
                                appExecutors.diskIO.execute {
                                    response.body()?.filmList?.let { filmRes ->
                                        for (item in filmRes) {
                                            val film = Film(
                                                item.filmID.toString(),
                                                item.name,
                                                "",
                                                (item.rating * 10).toInt().toString(),
                                                getGenreString(item.genre, genreList),
                                                "",
                                                "",
                                                IMAGE_API + item.image
                                            )
                                            tvList.add(film)
                                        }
                                    }
                                    tvshowResult.postValue(tvList)
                                    filmsDao.deleteAllTvShows()
                                    filmsDao.insertTvShows(tvList)
                                }

                            }

                        }

                        override fun onFailure(call: Call<FilmListResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
            }
        }

        return tvshowResult
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

    private fun checkAttrNull(item: Film?): Boolean {
        if (item == null) return true
        if (item.title.isEmpty()
            || item.genre.isEmpty()
            || item.image.isEmpty()
            || item.rating.isEmpty()
            || item.releaseDate.isEmpty()
            || item.description.isEmpty()
            || item.status.isEmpty()) return true
        return false
    }

    override fun getMovieDetails(id: String): LiveData<Film> {
        val localData = filmsDao.getMovieDetail(id)
        movieDetailResult.addSource(localData) {
            if (!checkAttrNull(it)) {
                movieDetailResult.removeSource(localData)
                movieDetailResult.value = it
            } else {
                networkConfig
                    .getService()
                    .getMovieDetails(id)
                    .enqueue(object : Callback<FilmResponse> {
                        override fun onResponse(
                            call: Call<FilmResponse>,
                            response: Response<FilmResponse>
                        ) {
                            var res: Film? = null
                            if (response.isSuccessful) {
                                appExecutors.diskIO.execute {
                                    response.body()?.let { item ->
                                        res = Film(
                                            item.filmID.toString(),
                                            item.title,
                                            if (item.description.isEmpty()) "Unknown" else item.description,
                                            (item.rating * 10).toInt().toString(),
                                            getGenreStringDetails(item.genreName),
                                            if (item.status.isEmpty()) "Unknown" else item.status,
                                            if (item.releaseDate.isEmpty()) "No date specified" else item.releaseDate,
                                            IMAGE_API + item.image
                                        )
                                    }
                                    movieDetailResult.postValue(res)
                                    res?.let { it1 -> filmsDao.updateMovieDetail(it1) }
                                }

                            }

                        }

                        override fun onFailure(call: Call<FilmResponse>, t: Throwable) {

                        }

                    })
            }
        }

        return movieDetailResult
    }

    override fun getTvShowDetails(id: String): LiveData<Film> {
        val localData = filmsDao.getTvShowDetail(id)
        tvshowDetailResult.addSource(localData) {
            if (!checkAttrNull(it)) {
                tvshowDetailResult.removeSource(localData)
                tvshowDetailResult.value = it
            } else {
                networkConfig
                    .getService()
                    .getTvShowDetails(id)
                    .enqueue(object : Callback<FilmResponse> {
                        override fun onResponse(
                            call: Call<FilmResponse>,
                            response: Response<FilmResponse>
                        ) {
                            var res: Film? = null
                            if (response.isSuccessful) {
                                appExecutors.diskIO.execute {
                                    response.body()?.let { item ->
                                        Log.d("<TAG>res", item.toString())
                                        res = Film(
                                            item.filmID.toString(),
                                            item.name,
                                            if (item.description.isEmpty()) "No Description" else item.description,
                                            (item.rating * 10).toInt().toString(),
                                            getGenreStringDetails(item.genreName),
                                            if (item.status.isEmpty()) "Unknown" else item.status,
                                            if (item.firstAirDate.isEmpty()) "No date specified" else item.firstAirDate,
                                            IMAGE_API + item.image
                                        )
                                    }
                                    tvshowDetailResult.postValue(res)
                                    res?.let { it1 -> filmsDao.updateTvShowDetail(it1) }
                                }

                            }

                        }

                        override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
            }
        }

        return tvshowDetailResult
    }

    companion object {
        @Volatile
        private var instance: FilmRepository? = null
        fun getInstance(
            networkConfig: NetworkConfig,
            filmsDao: FilmDao,
            appExecutors: AppExecutors
        ): FilmRepository =
            instance ?: synchronized(this) {
                instance ?: FilmRepository(networkConfig, filmsDao, appExecutors).apply {
                    instance = this
                }
            }
    }

}