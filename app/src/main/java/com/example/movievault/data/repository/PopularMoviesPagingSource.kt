package com.example.movievault.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movievault.data.mapper.toDomain
import com.example.movievault.data.remote.api.MovieVaultApiService
import com.example.movievault.domain.model.Movie

class PopularMoviesPagingSource(
    private val api: MovieVaultApiService
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1

            val response = api.getPopularMovies(page)

            LoadResult.Page(
                data = response.results.map { it.toDomain() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
         return state.anchorPosition
    }
}