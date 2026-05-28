package com.example.movievault.presentation.components

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toUserMessage(): String = when (this) {
    is UnknownHostException -> "No internet connection"
    is SocketTimeoutException -> "Connection timed out. Try again"
    is IOException -> "Network error. Check your connection"
    else -> message ?: "Something went wrong"
}