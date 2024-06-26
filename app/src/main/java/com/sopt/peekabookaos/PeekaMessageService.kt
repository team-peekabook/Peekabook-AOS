package com.sopt.peekabookaos

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sopt.peekabookaos.presentation.splash.SplashActivity
import timber.log.Timber

class PeekaMessageService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data[TITLE] ?: ""
            val body = remoteMessage.data[BODY] ?: ""
            sendNotification(title, body)
        }
    }

    private fun sendNotification(title: String, body: String) {
        createNotificationChannel()
        val intent = Intent(this, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notificationBuilder = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setContentText(body)
            .setAutoCancel(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID = "peeka_channel"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_NAME = "peeka_channel_name"
        private const val TITLE = "title"
        private const val BODY = "body"
    }
}
