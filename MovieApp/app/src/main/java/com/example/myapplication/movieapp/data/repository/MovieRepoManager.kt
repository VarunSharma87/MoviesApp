package com.example.myapplication.movieapp.data.repository

import com.example.myapplication.movieapp.data.db.MoviesDB
import com.example.myapplication.movieapp.data.db.entity.Favorites
import com.example.myapplication.movieapp.domain.model.Movie
import com.example.myapplication.movieapp.domain.model.PlayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepoManager @Inject constructor(
    private val moviesDB: MoviesDB,
    private val movieMapper: MovieMapper,
) :
    IMovieRepoManager {
    override suspend fun updatePlayListData(
        playList: PlayList,
        movieId: Int,
        favorites: Favorites?,
    ) {
        updatePlayListNameForMovie(movieId, playList)
        updateFavorites(movieId, playList.id)
    }

    override suspend fun getMoviesByPlayListId(playListId: String): List<Movie>? {
        return withContext(Dispatchers.IO) {
            val movieIds = moviesDB.favoritesDao().getMoviesInPlayList(playListId)
            movieIds?.let {
                val movies = moviesDB.movieDao().getMovies(movieIds)
                movieMapper.toDomainModel(movies)
            }
        }
    }

    private suspend fun updatePlayListNameForMovie(movieId: Int, playList: PlayList) {
        withContext(Dispatchers.IO) {
            val movieDao = moviesDB.movieDao()
            movieDao.updatePlayListName(movieId, playList.name)
        }
    }

    private suspend fun updateFavorites(movieId: Int, newPlayListId: String) {
        withContext(Dispatchers.IO) {
            val favoritesDao = moviesDB.favoritesDao()
            val favorite = favoritesDao.getFavoritesForMovie(movieId)
            if (favorite == null) {
                favoritesDao.insertFavorite(Favorites(System.currentTimeMillis().toString(),
                    newPlayListId,
                    movieId))
            } else {
                favoritesDao.updateFavoritesWithPlaylistId(newPlayListId, favorite.id)
            }
        }
    }
}