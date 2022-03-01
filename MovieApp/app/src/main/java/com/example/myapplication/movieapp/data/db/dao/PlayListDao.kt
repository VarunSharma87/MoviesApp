package com.example.myapplication.movieapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.movieapp.data.db.entity.PlayList

@Dao
interface PlayListDao {
    @Query("Update playlist Set movieIds = :movies Where id = :playListId")
    suspend fun updatePlayList(movies: IntArray, playListId: String)

    @Insert
    suspend fun insertPlayList(playListId: PlayList)

    @Query("Select * from playlist")
    fun getPlayLists(): List<PlayList>

    @Query("Delete from playlist")
    fun deleteAll()

}