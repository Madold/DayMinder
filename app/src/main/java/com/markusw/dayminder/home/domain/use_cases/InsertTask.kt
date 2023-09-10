package com.markusw.dayminder.home.domain.use_cases

import com.markusw.dayminder.core.domain.Task
import com.markusw.dayminder.core.domain.toEntity
import com.markusw.dayminder.core.domain.repository.TasksRepository
import javax.inject.Inject

class InsertTask @Inject constructor(
    private val repository: TasksRepository
) {

    suspend operator fun invoke(task: Task) = repository.insertTask(task.toEntity())

}