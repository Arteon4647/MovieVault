package com.example.movievault.data.remote.api

import com.example.movievault.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

const val API_KEY_QUERY = "api_key"

class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val newUrl = origin.url.newBuilder()
            .addQueryParameter(API_KEY_QUERY,BuildConfig.TMDB_API_KEY)
            .build()
        val newRequest = origin.newBuilder()
            .url(newUrl).build()
        return chain.proceed(newRequest)
    }
}