package com.markusw.dayminder.core.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.example.dayminder.R
import com.markusw.dayminder.MainActivity

object NotificationBuilder {

    private val DEFAULT_VIBRATION_PATTERN = longArrayOf(1000, 500, 1000, 500, 1000)
    fun createNotification(
        context: Context,
        chanelId: String,
        contentText: String,
        title: String,
        @DrawableRes smallIcon: Int = R.drawable.ic_stat_bell
    ): Notification {

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 123, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, chanelId)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSmallIcon(smallIcon)
            .setVibrate(DEFAULT_VIBRATION_PATTERN)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

}