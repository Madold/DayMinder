package com.markusw.dayminder.home.domain.use_cases

import com.markusw.dayminder.core.domain.repository.TasksRepository
import javax.inject.Inject

class GetTaskOrderedByTitle @Inject constructor(
    private val repository: TasksRepository
) {
    operator fun invoke() = repository.getTaskOrderedByTitle()
}