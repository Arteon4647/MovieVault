package com.example.movievault.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class ApiKeyInterceptor @Inject constructor(
    @Named("tmdb_api_key") private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val newUrl = origin.url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val newRequest = origin.newBuilder()
            .url(newUrl).build()
        return chain.proceed(newRequest)
    }
}