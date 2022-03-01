package com.example.myapplication.movieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.movieapp.data.db.entity.Favorites
import com.example.myapplication.movieapp.data.repository.IMovieRepoManager
import com.example.myapplication.movieapp.data.repository.IPlayListRepository
import com.example.myapplication.movieapp.domain.model.PlayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayListViewModel @Inject constructor(
    private val playListRepo: IPlayListRepository,
    private val repoManager: IMovieRepoManager,
) : ViewModel() {

    suspend fun getPlayLists(): List<PlayList> {
        return playListRepo.getPlayLists()
    }

    fun updatePlayList(playList: PlayList, movieId: Int) {
        viewModelScope.launch {
            repoManager.updatePlayListData(playList, movieId, null)
        }
    }

    fun createNewPlaylist(playList: PlayList, movieId: Int) {
        viewModelScope.launch {
            playListRepo.createNewPlayList(playList)
            repoManager.updatePlayListData(playList,
                movieId,
                Favorites(System.currentTimeMillis().toString(),
                    playList.id,
                    movieId))
        }
    }
}