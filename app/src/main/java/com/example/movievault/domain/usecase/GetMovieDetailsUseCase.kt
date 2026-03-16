package com.example.movievault.domain.usecase

import com.example.movievault.domain.model.MovieDetails
import com.example.movievault.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieDetails {
        return repository.getMovieDetails(movieId)
    }
}