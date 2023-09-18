package com.markusw.dayminder.core.utils

import com.markusw.dayminder.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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

        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getTimeZone("GMT")

        return dateFormatter.format(timestamp)
    }

    fun computeTimeStamp(dateInMillis: Long, hour: Int, minute: Int): Long {

        Timber.d("Computing timestamp for date: $dateInMillis, hour: $hour, minute: $minute")

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))

        calendar.timeInMillis = dateInMillis
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        Timber.d("Computed timestamp: ${calendar.timeInMillis}")

        return calendar.timeInMillis
    }

    fun formatHourFromTimestamp(timestamp: Long): String {
        val date = Date(timestamp)
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return sdf.format(date)
    }

    fun getGreetingStringId(): Int {
        val currentHour = LocalTime.now()
        val morningHour = LocalTime.of(6, 0)
        val afternoonHour = LocalTime.of(12, 0)
        val eveningHour = LocalTime.of(18, 0)

        return when {
            currentHour.isBefore(morningHour) -> R.string.morning_greeting
            currentHour.isBefore(afternoonHour) -> R.string.afternoon_greeting
            currentHour.isBefore(eveningHour) -> R.string.afternoon_greeting
            else -> R.string.evening_greeting
        }
    }

}