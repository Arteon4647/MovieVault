package com.example.movievault.data.remote.api


import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieVaultApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MovieVaultListResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieVaultListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieVaultDetailResponse
}