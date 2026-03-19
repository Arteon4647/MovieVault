package com.example.movievault.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movievault.data.local.db.FavoriteMovieDao
import com.example.movievault.data.mapper.toDomain
import com.example.movievault.data.mapper.toEntity
import com.example.movievault.data.remote.api.MovieVaultApiService
import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.model.MovieDetails
import com.example.movievault.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieVaultApiService,
    private val dao: FavoriteMovieDao
) : MovieRepository {
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PopularMoviesPagingSource(api) }
        ).flow
    }

    override suspend fun searchMovie(query: String, page: Int): List<Movie> {
        return api.searchMovie(query, page).results.map { it.toDomain() }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return api.getMovieDetails(movieId).toDomain()
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return dao.getAllFavorites()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addToFavorites(movie: Movie) {
        dao.insertFavorite(movie.toEntity())
    }

    override suspend fun removeFromFavorites(movieId: Int) {
        dao.deleteFavorite(movieId)
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
        return dao.isFavorite(movieId)
    }
}