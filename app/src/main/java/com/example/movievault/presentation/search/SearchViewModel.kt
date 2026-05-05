package com.example.movievault.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.usecase.GetFavoriteMoviesUseCase
import com.example.movievault.domain.usecase.SearchMoviesUseCase
import com.example.movievault.domain.usecase.ToggleFavoriteUseCase
import com.example.movievault.presentation.components.FavoriteDialogController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val favorites = getFavoriteMoviesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val dialogController = FavoriteDialogController()
    val dialogMovie = dialogController.dialogMovie

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<SearchUiState> = _searchQuery
        .debounce(600L)
        .distinctUntilChanged()
        .map { query ->
            query.trim().takeIf { it.length >= 2 }
        }
        .flatMapLatest { query ->
            if (query == null) {
                flow { emit(SearchUiState.Idle) }
            } else {
                flow {
                    emit(SearchUiState.Loading)
                    val movies = searchMoviesUseCase(query)
                    emit(
                        if (movies.isEmpty()) SearchUiState.Empty
                        else SearchUiState.Success(movies)
                    )
                }.catch { throwable ->
                    emit(SearchUiState.Error(throwable.message ?: "Search error"))
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Idle
        )

    fun onQueryChange(query: String) {
        _searchQuery.value = query
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

    fun confirmDelete() {
        dialogController.confirmDelete {
            toggleFavorite(it)
        }
    }

    fun dismissDialog() {
        dialogController.dismissDialog()
    }
}