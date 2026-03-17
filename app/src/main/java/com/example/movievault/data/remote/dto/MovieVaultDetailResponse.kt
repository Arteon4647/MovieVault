package com.example.movievault.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieVaultDetailResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("genres")
    val genres: List<GenreDto>
)

data class GenreDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
