package com.example.movievault.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object FavoriteRoute

@Serializable
data class DetailsRoute(val movieId: Int)