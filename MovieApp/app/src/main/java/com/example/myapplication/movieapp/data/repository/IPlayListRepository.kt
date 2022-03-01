package com.example.myapplication.movieapp.data.repository

import com.example.myapplication.movieapp.domain.model.PlayList

interface IPlayListRepository {
    suspend fun getPlayLists(): List<PlayList>
    suspend fun updatePlayList(playList: PlayList, movieId: Int)
    suspend fun createNewPlayList(playList: PlayList)
}