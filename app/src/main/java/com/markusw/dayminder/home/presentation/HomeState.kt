package com.markusw.dayminder.home.presentation

import com.markusw.dayminder.core.domain.Task

data class HomeState(
    val taskTitle: String = "",
    val taskTitleError: String? = null,
    val taskDescription: String = "",
    val taskList: List<Task> = listOf(),
    val isAddTaskDialogVisible: Boolean = false,
    val sortType: SortType = SortType.Title,
)
