package com.example.movievault.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movievault.R
import com.example.movievault.domain.model.Movie
import com.example.movievault.presentation.components.EmptyIcon
import com.example.movievault.presentation.components.EmptyState
import com.example.movievault.presentation.components.ErrorState
import com.example.movievault.presentation.components.LoadingState
import com.example.movievault.presentation.components.MovieCard
import com.example.movievault.presentation.components.MovieTextField
import com.example.movievault.presentation.components.MovieTopBar
import com.example.movievault.presentation.components.RemoveFromFavoritesDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onMovieClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    val dialogMovie by viewModel.dialogMovie.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Column {
            MovieTopBar(
                title = null,
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
            MovieTextField(
                value = searchQuery,
                onValueChange = viewModel::onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.search)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            when (val state = uiState) {
                is SearchUiState.Idle -> EmptyState(
                    message = stringResource(R.string.start_to_search),
                    subtitle = stringResource(R.string.min_2_symbols),
                    icon = EmptyIcon.SEARCH
                )

                is SearchUiState.Loading -> LoadingState()

                is SearchUiState.Empty -> EmptyState(
                    message = stringResource(R.string.nothing_found),
                    subtitle = "upon request «$searchQuery»",
                    icon = EmptyIcon.SEARCH
                )

                is SearchUiState.Success -> ResultsContent(
                    movies = state.movies,
                    favorites = favorites.map { it.id }.toSet(),
                    onMovieClick = onMovieClick,
                    onFavoriteClick = { movie, isFavorite ->
                        viewModel.onFavoriteClick(movie, isFavorite)
                    }
                )

                is SearchUiState.Error -> ErrorState(
                    message = state.message,
                    onRetry = { viewModel.onQueryChange(searchQuery) }
                )
            }
            if (dialogMovie != null) {
                RemoveFromFavoritesDialog(
                    onConfirm = { viewModel.confirmDelete() },
                    onDismiss = { viewModel.dismissDialog() }
                )
            }
        }
    }
}

@Composable
private fun ResultsContent(
    movies: List<Movie>,
    favorites: Set<Int>,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Movie, Boolean) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 170.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        items(items = movies, key = { it.id }) { movie ->
            val isFavorite = movie.id in favorites
            MovieCard(
                movie = movie,
                isFavorite = isFavorite,
                onClick = { onMovieClick(movie.id) },
                onFavoriteClick = { onFavoriteClick(movie, isFavorite) }
            )
        }
    }
}
