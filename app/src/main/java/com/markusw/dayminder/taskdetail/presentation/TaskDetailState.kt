package com.markusw.dayminder.taskdetail.presentation

import com.markusw.dayminder.core.domain.model.Task

data class TaskDetailState(
    val selectedTask: Task? = null,
)
