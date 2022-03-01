package com.example.myapplication.movieapp.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Int,
    val playListId: String?,
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
    val pageNo: Int,
    val playListName: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}