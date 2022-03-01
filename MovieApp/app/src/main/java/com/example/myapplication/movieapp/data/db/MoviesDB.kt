package com.example.myapplication.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.movieapp.data.db.dao.FavoritesDao
import com.example.myapplication.movieapp.data.db.dao.MoviesDao
import com.example.myapplication.movieapp.data.db.dao.PlayListDao
import com.example.myapplication.movieapp.data.db.entity.Favorites
import com.example.myapplication.movieapp.data.db.entity.Movie
import com.example.myapplication.movieapp.data.db.entity.PlayList

@Database(entities = [Movie::class, PlayList::class, Favorites::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDB : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
    abstract fun playListDao(): PlayListDao
    abstract fun favoritesDao(): FavoritesDao
}