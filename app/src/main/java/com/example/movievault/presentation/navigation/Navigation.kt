package com.example.movievault.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.movievault.presentation.details.DetailsScreen
import com.example.movievault.presentation.favorites.FavoritesScreen
import com.example.movievault.presentation.home.HomeScreen

@Composable
fun MovieVaultNavigation() {
    val backStack = remember {
        mutableStateListOf<Any>(HomeRoute)
    }
    val currentRoute = backStack.last()

    Scaffold(
        bottomBar = {
            if (currentRoute !is DetailsRoute) {
                BottomBar(
                    currentRoute = currentRoute,
                    onTabClick = { route ->
                        backStack.clear()
                        backStack.add(route)
                    }
                )
            }
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
                when(route) {
                    is HomeRoute -> NavEntry<Any>(route) {
                        HomeScreen()
                    }

                    is FavoritesRoute -> NavEntry<Any>(route) {
                        FavoritesScreen() {}
                    }

                    is DetailsRoute -> NavEntry<Any>(route) {
                        DetailsScreen {  }
                    }

                    else -> NavEntry(Unit) { Text("Unknown route") }
                }
            }
        )
    }
}