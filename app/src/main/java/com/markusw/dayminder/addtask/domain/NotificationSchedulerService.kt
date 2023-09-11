package com.markusw.dayminder.addtask.domain

interface NotificationSchedulerService {
    fun scheduleNotification(item: NotificationItem)
    fun cancelNotification(item: NotificationItem)
}

