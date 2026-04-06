package com.example.movievault.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopBar(
    title: String? = null,
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    isTransparent: Boolean = false,
    showAction: Boolean = true
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                )
            }
        },
        navigationIcon = {
            navigationIcon?.invoke()
        },
        actions = {
            if (showAction) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }

                IconButton(onClick = onFavoritesClick) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorites",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = if (isTransparent) Color.Transparent
            else MaterialTheme.colorScheme.tertiary,
            scrolledContainerColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.onTertiary
        ),
        scrollBehavior = scrollBehavior
    )
}