package com.markusw.dayminder.di

import android.content.Context
import androidx.room.Room
import com.markusw.dayminder.core.data.database.TaskDao
import com.markusw.dayminder.core.data.database.TaskDatabase
import com.markusw.dayminder.core.data.repository.AndroidTaskRepository
import com.markusw.dayminder.core.domain.repository.TasksRepository
import com.markusw.dayminder.core.utils.Constants.TASK_TABLE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context,
            TaskDatabase::class.java,
            TASK_TABLE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideTaskDao(taskDatabase: TaskDatabase) = taskDatabase.dao

    @Provides
    @Singleton
    fun provideTaskRepository(dao: TaskDao): TasksRepository = AndroidTaskRepository(dao)

}