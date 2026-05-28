package com.example.movievault.presentation.home

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.movievault.R
import com.example.movievault.presentation.components.ErrorState
import com.example.movievault.presentation.components.LoadingState
import com.example.movievault.presentation.components.MovieCard
import com.example.movievault.presentation.components.MovieTopBar
import com.example.movievault.presentation.components.RemoveFromFavoritesDialog
import com.example.movievault.presentation.components.toUserMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val dialogMovie by viewModel.dialogMovie.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Column {
            MovieTopBar(
                title = stringResource(R.string.app_name),
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorites",
                            tint = MaterialTheme.colorScheme.onTertiary
                        )
                    }

                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
            when {
                movies.loadState.refresh is LoadState.Loading -> LoadingState()

                movies.loadState.refresh is LoadState.Error -> {
                    val error = (movies.loadState.refresh as LoadState.Error).error
                    ErrorState(
                        message = error.toUserMessage(),
                        onRetry = { movies.retry() }
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 12.dp,
                            end = 12.dp,
                            bottom = 12.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            count = movies.itemCount,
                            key = movies.itemKey { it.id }
                        ) { index ->
                            val movie = movies[index]

                            if (movie != null) {
                                val isFavorite = favorites.any { it.id == movie.id }
                                MovieCard(
                                    movie = movie,
                                    isFavorite = isFavorite,
                                    onClick = { onMovieClick(movie.id) },
                                    onFavoriteClick =
                                        {
                                            viewModel.onFavoriteClick(
                                                movie,
                                                isFavorite
                                            )
                                        }
                                )
                            }
                        }
                        if (movies.loadState.append is LoadState.Loading) {
                            item {
                                LoadingState()
                            }
                        }
                        if (movies.loadState.append is LoadState.Error) {
                            item {
                                val error = (movies.loadState.append as LoadState.Error).error
                                ErrorState(
                                    message = error.toUserMessage(),
                                    onRetry = { movies.retry() },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
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