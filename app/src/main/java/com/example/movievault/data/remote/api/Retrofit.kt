package com.example.movievault.data.remote.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client = OkHttpClient.Builder()
    .addInterceptor(ApiKeyInterceptor())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()