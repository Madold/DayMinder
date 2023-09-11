package com.markusw.dayminder.addtask.domain

data class NotificationItem(
    val id: Int,
    val title: String,
    val message: String,
    val timestamp: Long
) {
    companion object {
        const val TITLE = "title"
        const val MESSAGE = "message"
    }
}

