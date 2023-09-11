package com.markusw.dayminder.addtask.data

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import com.markusw.dayminder.DayMinder
import com.markusw.dayminder.addtask.domain.NotificationItem
import com.markusw.dayminder.core.utils.NotificationBuilder

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationTitle = intent?.getStringExtra(NotificationItem.TITLE) ?: return
        val notificationMessage = intent.getStringExtra(NotificationItem.MESSAGE) ?: return

        val notification = NotificationBuilder.createNotification(
            context = context!!,
            chanelId = DayMinder.SCHEDULED_NOTIFICATIONS_CHANNEL_ID,
            contentText = notificationMessage,
            title = notificationTitle
        )
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)

    }
}