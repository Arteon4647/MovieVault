package com.example.movievault.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.usecase.GetFavoriteMoviesUseCase
import com.example.movievault.domain.usecase.ToggleFavoriteUseCase
import com.example.movievault.presentation.components.FavoriteDialogController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {
    private val _errors = MutableSharedFlow<String>()
    val errors = _errors.asSharedFlow()

    private val dialogController = FavoriteDialogController()
    val dialogMovie = dialogController.dialogMovie

    val favorites = getFavoriteMoviesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            runCatching {
                toggleFavoriteUseCase(movie)
            }.onFailure {
                _errors.emit("Failed to update favorites")
            }
        }
    }

    fun onFavoriteClick(
        movie: Movie,
        isFavorite: Boolean
    ) {
        dialogController.onFavoriteClick(
            movie = movie,
            isFavorite = isFavorite,
            toggleFavorite = ::toggleFavorite
        )
    }

    fun confirmDelete() {
        dialogController.confirmDelete(
            toggleFavorite = ::toggleFavorite
        )
    }

    fun dismissDialog() {
        dialogController.dismissDialog()
    }
}