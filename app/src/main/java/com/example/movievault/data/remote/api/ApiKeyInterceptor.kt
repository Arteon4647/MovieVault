package com.example.movievault.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response
import com.example.movievault.BuildConfig

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val newUrl = origin.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()
        val newRequest = origin.newBuilder()
            .url(newUrl).build()
        return chain.proceed(newRequest)
    }
}