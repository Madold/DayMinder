package com.markusw.dayminder.core.utils

import java.util.Calendar

object TimeUtils {

    fun getDeviceHourInTimestamp(): Long {
        val currentTime = Calendar.getInstance().time
        return currentTime.time
    }

}