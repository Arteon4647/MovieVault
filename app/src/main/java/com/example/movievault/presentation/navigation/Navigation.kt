package com.example.movievault.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.movievault.R
import com.example.movievault.presentation.components.MovieSearchFavoriteTopBar
import com.example.movievault.presentation.details.DetailsScreen
import com.example.movievault.presentation.favorites.FavoritesScreen
import com.example.movievault.presentation.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieVaultNavigation() {
    val backStack = remember {
        mutableStateListOf<Any>(HomeRoute)
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    fun navigateSingleTop(route: Any) {
        if (backStack.lastOrNull() != route) {
            backStack.add(route)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.onTertiary,
        topBar = {
            MovieSearchFavoriteTopBar(
                title = stringResource(R.string.app_name),
                onTitleClick = { navigateSingleTop(HomeRoute) },
                onSearchClick = {},
                onFavoritesClick = { navigateSingleTop(FavoritesRoute) },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        NavDisplay(
            modifier = Modifier.padding(padding),
            backStack = backStack,
            onBack = {
                if (backStack.size > 1) {
                    backStack.removeAt(backStack.lastIndex)
                }
            },
            entryProvider = { route ->
                when (route) {
                    is HomeRoute -> NavEntry(route) {
                        HomeScreen()
                    }

                    is FavoritesRoute -> NavEntry(route) {
                        FavoritesScreen {}
                    }

                    is DetailsRoute -> NavEntry(route) {
                        DetailsScreen {}
                    }

                    else -> NavEntry(Unit) { Text("Unknown route") }
                }
            }
        )
    }
}