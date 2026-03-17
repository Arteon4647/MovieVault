package com.example.movievault.data.remote.api

import com.example.movievault.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val newUrl = origin.url.newBuilder()
            .addQueryParameter("api_key", TMDB_API_KEY)
            .build()
        val newRequest = origin.newBuilder()
            .url(newUrl).build()
        return chain.proceed(newRequest)
    }
}