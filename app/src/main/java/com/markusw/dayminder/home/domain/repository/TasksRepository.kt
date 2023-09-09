package com.markusw.dayminder.home.domain.repository

import com.markusw.dayminder.home.data.model.TaskEntity
import com.markusw.dayminder.home.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun insertTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    fun getTaskOrderedByTimestamp(): Flow<List<TaskEntity>>
    fun getTaskOrderedByTitle(): Flow<List<TaskEntity>>
}