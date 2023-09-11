package com.markusw.dayminder

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.dayminder.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DayMinder : Application() {

    companion object {
        const val SCHEDULED_NOTIFICATIONS_CHANNEL_ID = "scheduled_notifications"
        const val SCHEDULED_NOTIFICATIONS_CHANNEL_NAME = "Scheduled notifications"
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            setupTimber()
        }

        setupNotificationChannels()
    }

    private fun setupTimber() {
        val formatStrategy = PrettyFormatStrategy
            .newBuilder()
            .showThreadInfo(true)
            .methodOffset(5)
            .tag("")
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, "-$tag", message, t)
            }
        })

        Timber.d("Starting app...")
    }

    private fun setupNotificationChannels() {
        val scheduledNotificationsChannel = NotificationChannel(
            SCHEDULED_NOTIFICATIONS_CHANNEL_ID,
            SCHEDULED_NOTIFICATIONS_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            enableVibration(true)
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(scheduledNotificationsChannel)
    }

}