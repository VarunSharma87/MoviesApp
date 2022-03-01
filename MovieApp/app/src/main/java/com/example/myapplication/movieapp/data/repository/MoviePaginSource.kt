package com.example.myapplication.movieapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.movieapp.data.repository.MoviesRepository
import com.example.myapplication.movieapp.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/*
class MoviePagingSource @Inject constructor(
    private val moviesRepo: IMovieRepository,
    private val mapper: MovieMapper
) : PagingSource<Int, Movie>() {
    private val startingIndex = 1
    private val count = 40
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: startingIndex
        val movies = moviesRepo.getMovies(pageIndex, count)
        return try {
            LoadResult.Page(
                mapper.toDomainModel(movies.),
                if (pageIndex == startingIndex) null else pageIndex - 1,
                pageIndex
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}*/
