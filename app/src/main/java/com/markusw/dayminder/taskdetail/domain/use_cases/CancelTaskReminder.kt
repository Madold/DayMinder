package com.markusw.dayminder.taskdetail.domain.use_cases

import com.markusw.dayminder.core.domain.NotificationItem
import com.markusw.dayminder.core.domain.NotificationSchedulerService
import javax.inject.Inject

class CancelTaskReminder @Inject constructor(
    private val notificationSchedulerService: NotificationSchedulerService
) {
    operator fun invoke(notification: NotificationItem) {
        notificationSchedulerService.cancelNotification(notification)
    }
}