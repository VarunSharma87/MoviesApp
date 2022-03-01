package com.example.myapplication.movieapp.data.repository

import com.example.myapplication.movieapp.data.db.dao.PlayListDao
import com.example.myapplication.movieapp.domain.model.PlayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayListRepository @Inject constructor(
    private val localSource: PlayListDao,
    private val mapper: PlayListMapper,
) :
    IPlayListRepository {
    override suspend fun getPlayLists(): List<PlayList> {
        return withContext(Dispatchers.IO) {
            mapper.toDomainModel(localSource.getPlayLists())
        }
    }

    override suspend fun updatePlayList(playList: PlayList, movieId: Int) {
        withContext(Dispatchers.IO) {
            val updatedMovieIds = playList.movieIds?.toMutableList() ?: mutableListOf<Int>()
            updatedMovieIds.add(movieId)
            localSource.updatePlayList(updatedMovieIds.toIntArray(), playList.id)
        }

    }

    override suspend fun createNewPlayList(playList: PlayList) {
        withContext(Dispatchers.IO) {
            localSource.insertPlayList(mapper.toEntityModel(playList))
        }
    }
}