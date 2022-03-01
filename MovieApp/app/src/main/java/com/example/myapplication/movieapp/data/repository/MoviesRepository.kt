package com.example.myapplication.movieapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication.movieapp.data.db.MoviesDB
import com.example.myapplication.movieapp.data.db.entity.Movie
import com.example.myapplication.movieapp.data.network.MoviesAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val localSource: MoviesDB,
    private val remoteSource: MoviesAPI,
    private val mapper: MovieMapper,
) : IMovieRepository {


    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMovies(maxCount: Int) = Pager(
        config = PagingConfig(maxCount),
        remoteMediator = MovieRemoteMediator(localSource, remoteSource, mapper)
    ) {
        localSource.movieDao().getMovies()
    }

    override suspend fun getMovie(movieId: Int): Movie {
        return localSource.movieDao().getMovie(movieId)
    }
}