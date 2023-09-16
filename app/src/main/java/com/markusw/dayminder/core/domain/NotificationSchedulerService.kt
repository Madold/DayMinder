package com.markusw.dayminder.core.domain

interface NotificationSchedulerService {
    fun scheduleNotification(item: NotificationItem)
    fun cancelNotification(item: NotificationItem)
}

