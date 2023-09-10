package com.markusw.dayminder.addtask.presentation

import com.markusw.dayminder.core.presentation.UiText

data class AddTaskState(
    val taskTitle: String = "",
    val taskTitleError: UiText? = null,
    val taskDescription: String = "",
    val isTimePickerVisible: Boolean = false,
    val isDatePickerVisible: Boolean = false,
    val selectedDateInMillis: Long? = null,
    val selectedHour: Int? = null,
    val selectedMinute: Int? = null,
    val isTaskScheduled: Boolean = false,
)
