package com.markusw.dayminder

import android.app.Application
import com.example.dayminder.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DayMinder: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {

        }

    }
}