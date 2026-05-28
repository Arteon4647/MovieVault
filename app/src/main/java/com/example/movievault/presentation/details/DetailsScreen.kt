package com.example.movievault.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movievault.presentation.components.ErrorState
import com.example.movievault.presentation.components.LoadingState
import com.example.movievault.presentation.components.MovieTopBar
import com.example.movievault.presentation.components.RemoveFromFavoritesDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movieId: Int,
    onBackClick: () -> Unit = {},
    viewModel: DetailsViewModel =
        hiltViewModel<DetailsViewModel, DetailsViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(movieId)
            }
        )
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    val dialogMovie by viewModel.dialogMovie.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        when (val state = state) {

            is DetailsUiState.Loading -> LoadingState()

            is DetailsUiState.Error -> ErrorState(
                message = state.message,
                onRetry = { viewModel.loadMovie() }
            )

            is DetailsUiState.Success -> {
                DetailsContent(
                    movie = state.movie,
                    isFavorite = isFavorite,
                    onFavoriteClick = viewModel::onFavoriteClick,
                    modifier = Modifier
                )
            }
        }

        if (dialogMovie != null) {
            RemoveFromFavoritesDialog(
                onConfirm = { viewModel.confirmDelete() },
                onDismiss = { viewModel.dismissDialog() }
            )
        }

        MovieTopBar(
            navigationIcon = {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            scrollBehavior = scrollBehavior,
            modifier = Modifier.align(Alignment.TopStart)
        )
    }
}