package com.markusw.dayminder.core.presentation

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): UiText
}