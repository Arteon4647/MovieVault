package com.example.movievault.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MovieVaultMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag("FCM").d("New token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.tag("FCM").d("Message received from: ${message.from}")

        val title = message.data["title"]
            ?: message.notification?.title
            ?: "Movie Vault"

        val body = message.data["body"]
            ?: message.notification?.body
            ?: return

        Timber.tag("FCM").d("Title: $title, Body: $body")

        NotificationHelper.showNotification(
            context = applicationContext,
            title = title,
            body = body
        )
    }
}