package com.markusw.dayminder.addtask.presentation

import com.markusw.dayminder.core.presentation.UiText

data class AddTaskState(
    val taskTitle: String = "",
    val taskTitleError: UiText? = null,
    val taskDescription: String = "",
    val isTimePickerVisible: Boolean = false,
    val isDatePickerVisible: Boolean = false,
    val selectedDateInMillis: Long = System.currentTimeMillis(),
    val selectedHour: Int = 0,
    val selectedMinute: Int = 0,
    val isTaskScheduled: Boolean = false,
)
