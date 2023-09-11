package com.markusw.dayminder

import android.app.Application
import com.example.dayminder.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DayMinder : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            setupTimber()
        }

    }

    private fun setupTimber() {
        val formatStrategy = PrettyFormatStrategy
            .newBuilder()
            .showThreadInfo(true)
            .methodOffset(5)
            .tag("")
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, "-$tag", message, t)
            }
        })

        Timber.d("Starting app...")
    }

}