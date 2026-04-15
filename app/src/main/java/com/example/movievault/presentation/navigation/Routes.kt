package com.example.movievault.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object FavoritesRoute

@Serializable
data class DetailsRoute(val movieId: Int)