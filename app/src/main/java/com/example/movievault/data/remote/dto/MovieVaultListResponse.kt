package com.example.movievault.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieVaultListResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieVaultDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
