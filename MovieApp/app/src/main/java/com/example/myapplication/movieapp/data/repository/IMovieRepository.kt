package com.example.myapplication.movieapp.data.repository

import androidx.paging.Pager
import com.example.myapplication.movieapp.data.db.entity.Movie

interface IMovieRepository {
    suspend fun getMovies(maxCount: Int): Pager<Int, Movie>
    suspend fun getMovie(movieId: Int): Movie
}