package com.example.movievault.presentation.details

import com.example.movievault.domain.model.MovieDetails

sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data class Success(val movie: MovieDetails) : DetailsUiState
    data class Error(val message: String) : DetailsUiState
}