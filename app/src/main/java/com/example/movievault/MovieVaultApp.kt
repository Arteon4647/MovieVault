package com.example.movievault

import android.app.Application
import com.example.movievault.logging.CrashlyticsTree
import com.example.movievault.notifications.NotificationHelper
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MovieVaultApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        NotificationHelper.createNotificationChannel(this)
        logFcmToken()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    private fun logFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.tag("FCM").d("Token: ${task.result}")
            } else {
                Timber.tag("FCM").e(task.exception, "Failed to get FCM token")
            }
        }
    }
}
