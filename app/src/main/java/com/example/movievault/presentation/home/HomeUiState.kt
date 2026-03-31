package com.example.movievault.presentation.home

import androidx.paging.PagingData
import com.example.movievault.domain.model.Movie
import kotlinx.coroutines.flow.Flow

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val movies: Flow<PagingData<Movie>>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}