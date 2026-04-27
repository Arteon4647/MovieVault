package com.example.movievault.presentation.components

import com.example.movievault.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoriteDialogController {
    private val _dialogMovie = MutableStateFlow<Movie?>(null)
    val dialogMovie = _dialogMovie.asStateFlow()

    fun onFavoriteClick(
        movie: Movie,
        isFavorite: Boolean,
        toggleFavorite: (Movie) -> Unit
    ) {
        if (isFavorite) {
            _dialogMovie.value = movie
        } else {
            toggleFavorite(movie)
        }
    }

    fun confirmDelete(
        toggleFavorite: (Movie) -> Unit
    ) {
        _dialogMovie.value?.let { toggleFavorite(it) }
        _dialogMovie.value = null
    }

    fun dismissDialog() {
        _dialogMovie.value = null
    }
}