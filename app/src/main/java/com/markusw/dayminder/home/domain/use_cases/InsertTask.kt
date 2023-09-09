package com.markusw.dayminder.home.domain.use_cases

import com.markusw.dayminder.home.data.model.TaskEntity
import com.markusw.dayminder.home.domain.model.Task
import com.markusw.dayminder.home.domain.model.toEntity
import com.markusw.dayminder.home.domain.repository.TasksRepository
import javax.inject.Inject

class InsertTask @Inject constructor(
    private val repository: TasksRepository
) {

    suspend operator fun invoke(task: Task) = repository.insertTask(task.toEntity())

}