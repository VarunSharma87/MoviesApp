package com.example.myapplication.movieapp.data.repository

import com.example.myapplication.movieapp.data.db.entity.Movie
import com.example.myapplication.movieapp.data.db.entity.PlayList as EntityModel
import com.example.myapplication.movieapp.domain.model.PlayList as DomainModel
import javax.inject.Inject

class PlayListMapper @Inject constructor() {
    fun toDomainModel(entityModel: List<EntityModel>): List<DomainModel> {
        val domainList = mutableListOf<DomainModel>()
        entityModel.forEach {
            domainList.add(DomainModel(
                it.id,
                it.name,
                it.movieIds
            ))
        }
        return domainList.toList()
    }

    fun toEntityModel(domainModel: DomainModel): EntityModel {
        return EntityModel(
            domainModel.id,
            domainModel.name,
            domainModel.movieIds
        )
    }
}