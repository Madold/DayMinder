package com.markusw.dayminder.core.domain.use_cases

import com.markusw.dayminder.core.domain.model.Task
import com.markusw.dayminder.core.domain.model.toEntity
import com.markusw.dayminder.core.domain.repository.TasksRepository
import javax.inject.Inject

class InsertTask @Inject constructor(
    private val repository: TasksRepository
) {

    suspend operator fun invoke(task: Task) = repository.insertTask(task.toEntity())

}