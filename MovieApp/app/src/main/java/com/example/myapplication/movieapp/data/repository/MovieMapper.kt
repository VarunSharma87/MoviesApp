package com.example.myapplication.movieapp.data.repository

import javax.inject.Inject
import com.example.myapplication.movieapp.data.db.entity.Movie as EntityModel
import com.example.myapplication.movieapp.data.network.MovieDTO as RemoteModel
import com.example.myapplication.movieapp.domain.model.Movie as DomainModel

class MovieMapper @Inject constructor() {

    fun toDomainModel(entityModel: List<EntityModel>): List<DomainModel> {
        val movies = mutableListOf<DomainModel>()
        entityModel.forEach { movie ->
            movies.add(DomainModel(
                movie.id,
                movie.title,
                movie.backdrop_path,
                movie.vote_average,
                movie.playListId,
                movie.playListName
            ))
        }
        return movies.toList()
    }

    fun toDomainModel(entityModel: EntityModel) = DomainModel(
        entityModel.id,
        entityModel.title,
        entityModel.backdrop_path,
        entityModel.vote_average,
        entityModel.playListId,
        entityModel.playListName
    )

    fun toEntityModel(remoteModel: List<RemoteModel>, pageNo: Int): List<EntityModel> {
        val movies = mutableListOf<EntityModel>()
        remoteModel.forEach { movie ->
            movies.add(EntityModel(
                movie.id,
                null,
                movie.adult,
                movie.backdrop_path,
                movie.original_language,
                movie.original_title,
                movie.overview,
                movie.popularity,
                movie.poster_path,
                movie.release_date,
                movie.title,
                movie.video,
                movie.vote_average,
                movie.vote_count,
                movie.genre_Ids,
                pageNo,
                null
            ))
        }
        return movies.toList()
    }
}