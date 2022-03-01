package com.example.myapplication.movieapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorites(
    @PrimaryKey val id: String,
    val listId: String,
    val movieId: Int
)