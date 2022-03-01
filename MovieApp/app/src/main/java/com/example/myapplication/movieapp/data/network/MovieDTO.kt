package com.example.myapplication.movieapp.data.network

import androidx.room.PrimaryKey

data class MovieDTO(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double?,
    val vote_count: Int?,
    val genre_Ids: IntArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieDTO

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
