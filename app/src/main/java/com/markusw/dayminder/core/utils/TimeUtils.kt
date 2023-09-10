package com.markusw.dayminder.core.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

object TimeUtils {

    fun getDeviceHourInTimestamp(): Long {
        val currentTime = Calendar.getInstance().time
        return currentTime.time
    }

    fun formatTime(hour: Int?, minute: Int?): String {
        if (hour == null || minute == null) return ""

        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return "Invalid time"
        }

        val period = if (hour < 12) "AM" else "PM"
        val formattedHour = if (hour == 0 || hour == 12) 12 else hour % 12

        return String.format("%02d:%02d %s", formattedHour, minute, period)
    }

    fun formatDateFromTimestamp(timestamp: Long?): String {
        if (timestamp == null) return ""

        val instant = Instant.ofEpochMilli(timestamp)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())
        return formatter.format(zonedDateTime)
    }

}