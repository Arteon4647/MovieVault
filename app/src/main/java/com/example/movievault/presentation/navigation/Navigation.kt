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

    fun navigateAdd(route: Any) {
        if (backStack.lastOrNull() != route) {
            backStack.add(route)
        }
    }

    NavDisplay(
        modifier = Modifier.padding(),
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },
        entryProvider = entryProvider {
            entry<HomeRoute> {
                HomeScreen(
                    onMovieClick = { movieId ->
                        navigateAdd(DetailsRoute(movieId))
                    },
                    onSearchClick = {},
                    onFavoritesClick = { navigateAdd(FavoritesRoute) }
                )
            }

            entry<FavoritesRoute> {
                FavoritesScreen(
                    onMovieClick = { movieId ->
                        navigateAdd(DetailsRoute(movieId))
                    },
                    onBackClick = { backStack.removeAt(backStack.lastIndex) },
                    onSearchClick = {}
                )
            }

            entry<DetailsRoute> { navEntry ->
                DetailsScreen(
                    movieId = navEntry.movieId,
                    onBackClick = { backStack.removeAt(backStack.lastIndex) }
                )
            }
        }
    )
}