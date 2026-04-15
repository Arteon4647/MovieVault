package com.example.movievault.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomBar(
    currentRoute: Any,
    onTabClick: (Any) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute is HomeRoute,
            onClick = { onTabClick(HomeRoute) },
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentRoute is FavoritesRoute,
            onClick = {onTabClick(FavoritesRoute)},
            icon = {Icon(Icons.Default.Favorite, null)},
            label = {Text("Favorites")}
        )
    }
}