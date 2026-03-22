package com.example.movievault.data.mapper

import com.example.movievault.data.remote.dto.MovieVaultDetailResponse
import com.example.movievault.domain.model.MovieDetails

fun MovieVaultDetailResponse.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview ?: "",
        posterPath = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropPath = posterPath?.let { "https://image.tmdb.org/t/p/w780$it" },
        releaseDate = releaseDate ?: "",
        rating = voteAverage.toDouble(),
        genres = genres.map { it.name }
    )
}