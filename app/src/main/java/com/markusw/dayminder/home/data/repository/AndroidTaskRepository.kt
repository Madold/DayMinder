package com.markusw.dayminder.home.data.repository

import com.markusw.dayminder.home.data.database.TaskDao
import com.markusw.dayminder.home.data.model.TaskEntity
import com.markusw.dayminder.home.domain.repository.TasksRepository
import javax.inject.Inject

class AndroidTaskRepository @Inject constructor(
    private val taskDao: TaskDao
) : TasksRepository {
    override suspend fun insertTask(task: TaskEntity) = taskDao.insertTask(task)

    override suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)

    override fun getTaskOrderedByTimestamp() = taskDao.getTaskOrderedByTimestamp()

    override fun getTaskOrderedByTitle() = taskDao.getTaskOrderedByTitle()

}