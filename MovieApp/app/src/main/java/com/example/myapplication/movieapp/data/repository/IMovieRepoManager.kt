package com.example.myapplication.movieapp.data.repository

import com.example.myapplication.movieapp.data.db.entity.Favorites
import com.example.myapplication.movieapp.domain.model.Movie
import com.example.myapplication.movieapp.domain.model.PlayList

interface IMovieRepoManager {
    suspend fun updatePlayListData(playList: PlayList, movieId: Int, favorites: Favorites?)
    suspend fun getMoviesByPlayListId(playListId: String): List<Movie>?
}