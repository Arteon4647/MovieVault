package com.example.movievault.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieVaultListResponse(
    val page: Int,
    val results: List<MovieVaultDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
