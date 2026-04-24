package com.example.movievault.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.movievault.R
import com.example.movievault.presentation.components.FavoriteIconButtonWithDialog
import com.example.movievault.presentation.components.MovieCard
import com.example.movievault.presentation.components.MovieSearchFavoriteTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onTitleClick: () -> Unit,
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    val favorites by viewModel.favorites.collectAsState()
    val dialogMovieId by viewModel.dialogMovieId.collectAsState()
    val favoritesIds = remember(favorites) { favorites.mapTo(mutableSetOf()) { it.id } }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Column {
            MovieSearchFavoriteTopBar(
                title = stringResource(R.string.app_name),
                onTitleClick = onTitleClick,
                onSearchClick = onSearchClick,
                onFavoritesClick = onFavoritesClick,
                scrollBehavior = scrollBehavior
            )
            when (state) {
                is HomeUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is HomeUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = state.message)
                    }
                }

                is HomeUiState.Success -> {
                    val movies = state.movies.collectAsLazyPagingItems()

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
                                val isFavorite = movie.id in favoritesIds
                                MovieCard(
                                    movie = movie,
                                    onClick = { onMovieClick(movie.id) },
                                    favoriteButton = { modifier ->
                                        FavoriteIconButtonWithDialog(
                                            isFavorite = isFavorite,
                                            showDialog = dialogMovieId == movie.id,
                                            onClick = {
                                                viewModel.onFavoriteClick(
                                                    movie,
                                                    isFavorite
                                                )
                                            },
                                            onConfirm = { viewModel.confirmDelete(movie) },
                                            onDismiss = { viewModel.dismissDialog() },
                                            modifier = modifier
                                                .size(36.dp)
                                                .background(
                                                    Color.Black.copy(alpha = 0.5f),
                                                    CircleShape
                                                )
                                        )
                                    }
                                )
                            }
                        }

                        if (movies.loadState.append is LoadState.Loading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}