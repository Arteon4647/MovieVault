package com.example.movievault.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val rating: Double,
    val genres: List<String>,
    val isFavorite: Boolean = false
)