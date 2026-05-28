package com.example.movievault.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.usecase.GetFavoriteMoviesUseCase
import com.example.movievault.domain.usecase.GetMoviesUseCase
import com.example.movievault.domain.usecase.ToggleFavoriteUseCase
import com.example.movievault.presentation.components.FavoriteDialogController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    val movies: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { getMoviesUseCase() }
    ).flow.cachedIn(viewModelScope)
    val favorites = getFavoriteMoviesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val dialogController = FavoriteDialogController()
    val dialogMovie = dialogController.dialogMovie

    private fun toggleFavorite(movie: Movie) {
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

    fun confirmDelete() {
        dialogController.confirmDelete {
            toggleFavorite(it)
        }
    }

    fun dismissDialog() {
        dialogController.dismissDialog()
    }
}