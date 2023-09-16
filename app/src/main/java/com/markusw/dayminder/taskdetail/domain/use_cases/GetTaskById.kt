package com.markusw.dayminder.taskdetail.domain.use_cases

import com.markusw.dayminder.core.domain.repository.TasksRepository
import javax.inject.Inject

class GetTaskById @Inject constructor(
    private val tasksRepository: TasksRepository
) {

    suspend operator fun invoke(id: Int) = tasksRepository.getTaskById(id)

}