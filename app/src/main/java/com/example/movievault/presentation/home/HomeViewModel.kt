package com.example.movievault.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.usecase.GetFavoriteMoviesUseCase
import com.example.movievault.domain.usecase.GetPopularMoviesUseCase
import com.example.movievault.domain.usecase.ToggleFavoriteUseCase
import com.example.movievault.presentation.components.FavoriteDialogController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()
    val favorites = getFavoriteMoviesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val dialogController = FavoriteDialogController()
    val dialogMovieId = dialogController.dialogMovieId

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val movies = getPopularMoviesUseCase()
                _uiState.value = HomeUiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
        }
    }

    fun onFavoriteClick(movie: Movie, isFavorite: Boolean) {
        dialogController.onFavoriteClick(
            movie = movie,
            isFavorite = isFavorite
        ) {
            toggleFavorite(it)
        }
    }

    fun confirmDelete(movie: Movie) {
        dialogController.confirmDelete(movie) {
            toggleFavorite(it)
        }
    }

    fun dismissDialog() {
        dialogController.dismissDialog()
    }
}