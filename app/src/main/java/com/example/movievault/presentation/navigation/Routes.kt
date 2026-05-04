package com.example.movievault.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface MovieVaultNavKey : NavKey

@Serializable
data object HomeRoute : MovieVaultNavKey

@Serializable
data object FavoritesRoute : MovieVaultNavKey

@Serializable
data object SearchRoute : MovieVaultNavKey

@Serializable
data class DetailsRoute(val movieId: Int) : MovieVaultNavKey