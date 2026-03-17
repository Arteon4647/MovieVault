package com.example.movievault.domain.usecase

import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int): List<Movie> {
        return repository.searchMovie(query, page)
    }
}