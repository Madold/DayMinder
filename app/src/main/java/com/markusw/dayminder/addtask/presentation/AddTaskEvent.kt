package com.markusw.dayminder.addtask.presentation

sealed interface AddTaskEvent {
    data object TaskSavedSuccessfully : AddTaskEvent
}