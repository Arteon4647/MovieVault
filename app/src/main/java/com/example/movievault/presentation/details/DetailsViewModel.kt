package com.example.movievault.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movievault.data.mapper.toMovie
import com.example.movievault.domain.usecase.GetFavoriteMoviesUseCase
import com.example.movievault.domain.usecase.GetMovieDetailsUseCase
import com.example.movievault.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    init {
        loadMovie()
        observeFavorite()
    }

    private fun loadMovie() {
        viewModelScope.launch {
            try {
                val movie = getMovieDetailsUseCase(movieId)
                _uiState.value = DetailsUiState.Success(movie)
            } catch (e: Exception) {
                _uiState.value = DetailsUiState.Error(e.message ?: "Error")
            }
        }
    }

    private fun observeFavorite() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect { favorites ->
                _isFavorite.value = favorites.any { it.id == movieId }
            }
        }
    }

    fun toggleFavorite() {
        val currentState = _uiState.value
        if(currentState is DetailsUiState.Success) {
            val movie = currentState.movie ?: return
            viewModelScope.launch {
                toggleFavoriteUseCase(movie.toMovie())
            }
        }
    }
}