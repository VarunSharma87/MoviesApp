package com.example.myapplication.movieapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {
    @GET(nowPlaying)
    suspend fun getMovies(@Query("page") page: Int): MovieNetworkResponse

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        private const val key = "api_key=38a73d59546aa378980a88b645f487fc"
        private const val language = "en-US"
        private const val nowPlaying = "now_playing?$key&$language"
    }
}