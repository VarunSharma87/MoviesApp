package com.example.myapplication.movieapp.data.network

import com.example.myapplication.movieapp.data.db.entity.Movie
import com.google.gson.annotations.SerializedName

data class MovieNetworkResponse(
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    @SerializedName("results")
    val movies: List<MovieDTO>
)
