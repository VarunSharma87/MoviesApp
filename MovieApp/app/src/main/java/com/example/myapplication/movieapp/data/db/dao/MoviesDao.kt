package com.example.myapplication.movieapp.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.movieapp.data.db.entity.Movie

@Dao
interface MoviesDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("Update movies set playListName = :playListName where id = :movieId")
    suspend fun updatePlayListName(movieId: Int, playListName: String)

    @Query("Select * from movies")
    fun getMovies(): PagingSource<Int, Movie>

    //@Query("Select * from movies where id in (Select movieId from favorites where listId = :playListId)")
    @Query("Select * from movies where id in (:movieIds)")
    fun getMovies(movieIds: List<Int>): List<Movie>

    @Query("Delete from movies")
    fun deleteAll()

    @Query("Select * from movies Where id = :id")
    suspend fun getMovie(id: Int): Movie

}