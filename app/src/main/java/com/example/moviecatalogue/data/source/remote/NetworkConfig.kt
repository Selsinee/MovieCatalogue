package com.example.moviecatalogue.data.source.remote


import com.example.moviecatalogue.BuildConfig
import com.example.moviecatalogue.data.source.remote.`interface`.IFilm
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Seline on 03/01/2022 19:01
 */
class NetworkConfig {

    private val apiKey = BuildConfig.API_KEY

    private fun getInterceptor(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", apiKey)
                    .build()

                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
//                val newRequest: Request = chain.request().newBuilder()
//                    .add
//                    .build()
                chain.proceed(request)
            }
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): IFilm = getRetrofit().create(IFilm::class.java)
}
