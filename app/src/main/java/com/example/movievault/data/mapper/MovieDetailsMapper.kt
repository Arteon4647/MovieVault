package com.example.movievault.data.mapper

import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.model.MovieDetails

fun MovieDetails.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        rating = rating,
        releaseDate = releaseDate,
        overview = overview
    )
}