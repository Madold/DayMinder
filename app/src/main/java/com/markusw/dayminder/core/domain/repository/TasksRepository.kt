package com.markusw.dayminder.core.domain.repository

import com.markusw.dayminder.core.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun insertTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    fun getTaskOrderedByTimestamp(): Flow<List<TaskEntity>>
    fun getTaskOrderedByTitle(): Flow<List<TaskEntity>>
}