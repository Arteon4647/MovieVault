package com.example.movievault.data.remote.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val client = OkHttpClient.Builder()
    .addInterceptor(ApiKeyInterceptor())
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()