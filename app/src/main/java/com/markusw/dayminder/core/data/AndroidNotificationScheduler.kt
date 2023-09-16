package com.markusw.dayminder.core.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.markusw.dayminder.core.domain.NotificationItem
import com.markusw.dayminder.core.domain.NotificationSchedulerService
import java.util.TimeZone

class AndroidNotificationScheduler(private val context: Context) : NotificationSchedulerService {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    override fun scheduleNotification(item: NotificationItem) {

        val timestampGMT = item.timestamp
        val localZone = TimeZone.getDefault()
        val offset = localZone.getOffset(timestampGMT)
        val localTimestamp = timestampGMT - offset

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(NotificationItem.TITLE, item.title)
            putExtra(NotificationItem.MESSAGE, item.message)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            localTimestamp,
            PendingIntent.getBroadcast(
                context,
                item.id,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancelNotification(item: NotificationItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.id,
                Intent(context, NotificationReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}