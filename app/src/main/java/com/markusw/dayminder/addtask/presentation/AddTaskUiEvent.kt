package com.markusw.dayminder.addtask.presentation

sealed interface AddTaskUiEvent {
    data class ChangeTaskTitle(val taskTitle: String) : AddTaskUiEvent
    data class ChangeTaskDescription(val taskDescription: String) : AddTaskUiEvent
    data object SaveTask : AddTaskUiEvent
    data object ShowTimePicker : AddTaskUiEvent
    data object HideTimePicker : AddTaskUiEvent
    data object ShowDatePicker : AddTaskUiEvent
    data object HideDatePicker : AddTaskUiEvent
    data class ChangeSelectedDate(val selectedDateInMillis: Long) : AddTaskUiEvent
    data class ChangeSelectedHour(val selectedHour: Int) : AddTaskUiEvent
    data class ChangeSelectedMinute(val selectedMinute: Int) : AddTaskUiEvent
    data class ChangeTaskScheduled(val scheduled: Boolean) : AddTaskUiEvent
    data class ChangeTaskImportance(val isImportant: Boolean): AddTaskUiEvent

}