package com.example.movievault.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieVaultDetailResponse(
    val id: Int,
    val title: String,
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Float,
    val genres: List<GenreDto>
)

data class GenreDto(
    val id: Int,
    val name: String
)
