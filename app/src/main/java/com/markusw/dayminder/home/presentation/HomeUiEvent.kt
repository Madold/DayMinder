package com.markusw.dayminder.home.presentation

import com.markusw.dayminder.core.domain.model.Task

sealed interface HomeUiEvent {

    data class ChangeSortType(val sortType: SortType) : HomeUiEvent
    data class DeleteTask(val task: Task): HomeUiEvent
    data class ToggleTask(val task: Task): HomeUiEvent

}