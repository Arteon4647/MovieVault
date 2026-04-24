package com.example.movievault.presentation.components

import com.example.movievault.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoriteDialogController {
    private val _dialogMovieId = MutableStateFlow<Int?>(null)
    val dialogMovieId = _dialogMovieId.asStateFlow()

    fun onFavoriteClick(
        movie: Movie,
        isFavorite: Boolean,
        toggleFavorite: (Movie) -> Unit
    ) {
        if (isFavorite) {
            _dialogMovieId.value = movie.id
        } else {
            toggleFavorite(movie)
        }
    }

    fun confirmDelete(
        movie: Movie,
        toggleFavorite: (Movie) -> Unit
    ) {
        _dialogMovieId.value = null
        toggleFavorite(movie)
    }

    fun dismissDialog() {
        _dialogMovieId.value = null
    }
}