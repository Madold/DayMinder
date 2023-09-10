package com.markusw.dayminder.addtask.presentation

import com.markusw.dayminder.core.presentation.UiText

data class AddTaskState(
    val taskTitle: String = "",
    val taskTitleError: UiText? = null,
    val taskDescription: String = ""
)
