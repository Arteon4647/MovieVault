package com.example.movievault.domain.usecase

import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        val isFavorite = repository.isFavorite(movie.id)
        if (isFavorite) {
            repository.removeFromFavorites(movie.id)
        } else {
            repository.addToFavorites(movie)
        }

    }
}