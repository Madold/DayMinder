package com.markusw.dayminder.core.presentation

import com.markusw.dayminder.R

class PostNotificationsPermissionProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): UiText {
        return if (isPermanentlyDeclined) UiText.StringResource(R.string.post_notification_permission_permanently_denied)
        else UiText.StringResource(R.string.post_notification_permission_denied)
    }
}