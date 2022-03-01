package com.example.myapplication.movieapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.movieapp.data.db.entity.Favorites

@Dao
interface FavoritesDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(favorites: Favorites)

    @Query("Update favorites set listId = :listId where id = :id")
    suspend fun updateFavoritesWithPlaylistId(listId: String, id: String)

    @Query("Select * from favorites where movieId = :movieId")
    suspend fun getFavoritesForMovie(movieId: Int): Favorites?

    @Query("Select movieId from favorites where listId = :playListId")
    suspend fun getMoviesInPlayList(playListId: String): List<Int>?
}