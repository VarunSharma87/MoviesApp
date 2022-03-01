package com.example.myapplication.movieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.room.PrimaryKey
import com.example.myapplication.movieapp.data.db.MoviesDB
import com.example.myapplication.movieapp.data.network.MoviesAPI
import com.example.myapplication.movieapp.data.repository.*
import com.example.myapplication.movieapp.domain.model.Movie
import com.example.myapplication.movieapp.domain.model.PlayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesDB: MoviesDB,
    private val movieMapper: MovieMapper,
    private val remoteSource: MoviesAPI,
    private val moviesRepoManager: IMovieRepoManager
) : ViewModel() {

    private val movieDao = moviesDB.movieDao()
    private val _filterKey = MutableLiveData<String>(null)
    private val filterKey: LiveData<String>
        get() = _filterKey

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(pageSize = 50),
        remoteMediator = MovieRemoteMediator(moviesDB, remoteSource, movieMapper)
    ) {
        movieDao.getMovies()
    }.flow
        .map {
            it.map { movie -> movieMapper.toDomainModel(movie) }
        }

    suspend fun getMoviesInPlayList(playListId: String): List<Movie>? {
        return moviesRepoManager.getMoviesByPlayListId(playListId)
    }

    fun filterKey(): LiveData<String> {
        return filterKey
    }

    fun setFilterKey(key: String) {
        _filterKey.postValue(key)
    }
}