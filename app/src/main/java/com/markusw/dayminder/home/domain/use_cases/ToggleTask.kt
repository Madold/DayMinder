package com.markusw.dayminder.home.domain.use_cases

import com.markusw.dayminder.core.domain.model.Task
import com.markusw.dayminder.core.domain.model.toEntity
import com.markusw.dayminder.core.domain.repository.TasksRepository
import javax.inject.Inject

class ToggleTask @Inject constructor(
    private val repository: TasksRepository
) {

    suspend operator fun invoke(task: Task) {
        task.copy(isDone = !task.isDone).also {
            repository.insertTask(it.toEntity())
        }
    }

}