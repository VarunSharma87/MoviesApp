package com.example.myapplication.movieapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myapplication.movieapp.data.db.MoviesDB
import com.example.myapplication.movieapp.data.db.entity.Movie
import com.example.myapplication.movieapp.data.network.MoviesAPI
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val localSource: MoviesDB,
    private val remoteSource: MoviesAPI,
    private val mapper: MovieMapper
) : RemoteMediator<Int, Movie>() {
    private val defaultIndex = 1
    private val moviesDao = localSource.movieDao()
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                REFRESH -> defaultIndex
                PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    lastItem.pageNo
                }
            }
            val response = remoteSource.getMovies(loadKey)
            localSource.withTransaction {
                if (loadType == REFRESH) {
                    moviesDao.deleteAll()
                }
                moviesDao.insertAll(mapper.toEntityModel(response.movies, response.page))
            }

            MediatorResult.Success(
                endOfPaginationReached = response.movies.isEmpty()
            )
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
}