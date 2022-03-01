package com.example.myapplication.movieapp.domain.model

data class Movie(
    val id: Int,
    val name: String,
    val thumbnailUrl: String,
    val rating: Double?,
    val playListId: String?,
    val playListName: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
