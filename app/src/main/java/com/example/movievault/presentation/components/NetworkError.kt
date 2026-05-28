package com.example.movievault.presentation.components

import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toUserMessage(): String = when (this) {
    is UnknownHostException -> "No internet connection"
    is SocketTimeoutException -> "Connection timed out. Try again"
    is IOException -> "Network error. Check your connection"
    else -> message ?: "Something went wrong"
}

fun Throwable.logError(tag: String = "MovieVault", message: String = "Error occurred") {
    Timber.tag(tag).e(this, message)
}