package com.example.movievault.domain.repository

import androidx.paging.PagingData
import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
    suspend fun searchMovie(query: String, page: Int): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetails?
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun addToFavorites(movie: Movie)
    suspend fun removeFromFavorites(movieId: Int)
    suspend fun isFavorite(movieId: Int): Boolean
}