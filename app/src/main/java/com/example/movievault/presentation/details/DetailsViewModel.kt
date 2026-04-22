package com.example.movievault.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movievault.data.mapper.toMovie
import com.example.movievault.domain.model.MovieDetails
import com.example.movievault.domain.usecase.GetFavoriteMoviesUseCase
import com.example.movievault.domain.usecase.GetMovieDetailsUseCase
import com.example.movievault.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private var currentMovie: MovieDetails? = null

    fun load(movieId: Int) {
        loadMovie(movieId)
    }

    private fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            try {
                val movie = getMovieDetailsUseCase(movieId)
                if (movie != null) {
                    currentMovie = movie
                    _uiState.value = DetailsUiState.Success(movie)
                } else {
                    _uiState.value = DetailsUiState.Error("Movie not found")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = DetailsUiState.Error(e.message ?: "Error")
            }
        }
        observeFavorite(movieId)
    }

    private fun observeFavorite(movieId: Int) {
        viewModelScope.launch {
            getFavoriteMoviesUseCase()
                .catch { e ->
                    Log.e("DetailViewModel", "Favorites error", e)
                    emit(emptyList())
                }
                .collect { favorites ->
                    _isFavorite.value = favorites.any { it.id == movieId }
                }
        }
    }

    fun toggleFavorite() {
        val currentState = _uiState.value
        if (currentState is DetailsUiState.Success) {
            val movie = currentMovie ?: return
            viewModelScope.launch {
                try {
                    toggleFavoriteUseCase(movie.toMovie())
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    _uiState.value = DetailsUiState.Error(e.message ?: "Failed to update favorite")
                }
            }
        }
    }
}