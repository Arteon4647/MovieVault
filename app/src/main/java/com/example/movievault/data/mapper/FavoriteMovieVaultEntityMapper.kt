package com.example.movievault.data.mapper

import com.example.movievault.data.local.entity.FavoriteMovieEntity
import com.example.movievault.domain.model.Movie

fun Movie.toEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        title = title,
        posterPath = posterPath?.removePrefix("https://image.tmdb.org/t/p/w500"),
        voteAverage = rating,
        releaseDate = releaseDate,
        overview = overview
    )
}