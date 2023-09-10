package com.markusw.dayminder.addtask.presentation

sealed interface AddTaskUiEvent {
    data class ChangeTaskTitle(val taskTitle: String) : AddTaskUiEvent
    data class ChangeTaskDescription(val taskDescription: String) : AddTaskUiEvent
    data object SaveTask : AddTaskUiEvent

}