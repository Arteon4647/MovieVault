package com.example.movievault.domain.usecase

import androidx.paging.PagingData
import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return repository.getPopularMovies()
    }
}