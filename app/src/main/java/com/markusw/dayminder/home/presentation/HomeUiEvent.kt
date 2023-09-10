package com.markusw.dayminder.home.presentation

import com.markusw.dayminder.core.domain.Task

sealed interface HomeUiEvent {

    data class ChangeTaskTitle(val title: String) : HomeUiEvent
    data class ChangeTaskDescription(val description: String) : HomeUiEvent
    data class ChangeSortType(val sortType: SortType) : HomeUiEvent
    data object ShowAddTaskDialog : HomeUiEvent
    data object DismissAddTaskDialog : HomeUiEvent
    data object AddTask : HomeUiEvent
    data class DeleteTask(val task: Task): HomeUiEvent

}