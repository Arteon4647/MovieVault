package com.example.movievault.data.mapper

import com.example.movievault.data.remote.dto.MovieVaultDto
import com.example.movievault.domain.model.Movie

fun MovieVaultDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        overview = overview ?: "",
        releaseDate = releaseDate ?: "",
        rating = voteAverage.toDouble()
    )
}