package com.example.movievault.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.movievault.presentation.details.DetailsScreen
import com.example.movievault.presentation.favorites.FavoritesScreen
import com.example.movievault.presentation.home.HomeScreen

@Composable
fun MovieVaultNavigation() {
    val backStack = remember {
        mutableStateListOf<Any>(HomeRoute)
    }

    fun navigateTo(route: Any) {
        if (backStack.lastOrNull() != route) {
            backStack.add(route)
        }
    }

    fun navigateUp() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    NavDisplay(
        modifier = Modifier.padding(),
        backStack = backStack,
        onBack = { navigateUp() },
        entryProvider = entryProvider {
            entry<HomeRoute> {
                HomeScreen(
                    onMovieClick = { movieId ->
                        navigateTo(DetailsRoute(movieId))
                    },
                    onSearchClick = {},
                    onFavoritesClick = { navigateTo(FavoritesRoute) }
                )
            }

            entry<FavoritesRoute> {
                FavoritesScreen(
                    onMovieClick = { movieId ->
                        navigateTo(DetailsRoute(movieId))
                    },
                    onBackClick = { navigateUp() },
                    onSearchClick = {}
                )
            }

            entry<DetailsRoute> { navEntry ->
                DetailsScreen(
                    movieId = navEntry.movieId,
                    onBackClick = { navigateUp() }
                )
            }
        }
    )
}