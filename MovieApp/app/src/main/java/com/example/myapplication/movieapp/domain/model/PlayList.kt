package com.example.myapplication.movieapp.domain.model

data class PlayList(
    val id: String,
    val name: String,
    val movieIds: IntArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayList

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
