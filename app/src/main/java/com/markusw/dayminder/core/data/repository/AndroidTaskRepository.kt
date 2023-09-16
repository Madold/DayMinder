package com.markusw.dayminder.core.data.repository

import com.markusw.dayminder.core.data.database.TaskDao
import com.markusw.dayminder.core.data.model.TaskEntity
import com.markusw.dayminder.core.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AndroidTaskRepository @Inject constructor(
    private val taskDao: TaskDao
) : TasksRepository {
    override suspend fun insertTask(task: TaskEntity) = taskDao.insertTask(task)
    override suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)
    override fun getDailyTasks(): Flow<List<TaskEntity>> = taskDao.getDailyTasks()
    override fun getImportantTasks(): Flow<List<TaskEntity>> = taskDao.getImportantTasks()
    override suspend fun getTaskById(id: Int): TaskEntity = taskDao.getTaskById(id)

}