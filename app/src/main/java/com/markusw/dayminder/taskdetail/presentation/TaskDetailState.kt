package com.markusw.dayminder.taskdetail.presentation

import com.markusw.dayminder.core.domain.model.Task
import com.markusw.dayminder.core.presentation.UiText

data class TaskDetailState(
    val selectedTask: Task? = null,
    val isCancelReminderDialogVisible: Boolean = false,
    val taskTitleError: UiText? = null,
)
