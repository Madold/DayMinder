package com.markusw.dayminder.di

import android.content.Context
import com.markusw.dayminder.core.data.AndroidNotificationScheduler
import com.markusw.dayminder.core.domain.NotificationSchedulerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddTaskModule {

    @Provides
    @Singleton
    fun provideNotificationSchedulerService(@ApplicationContext context: Context): NotificationSchedulerService {
        return AndroidNotificationScheduler(context)
    }

}