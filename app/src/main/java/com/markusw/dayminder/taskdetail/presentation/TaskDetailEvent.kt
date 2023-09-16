package com.markusw.dayminder.taskdetail.presentation

sealed interface TaskDetailEvent {

    data class ChangeTaskTitle(val title: String): TaskDetailEvent
    data class ChangeTaskDescription(val description: String): TaskDetailEvent
    data object SaveChanges: TaskDetailEvent
    data object ChangesAppliedSuccessfully: TaskDetailEvent
    data object CancelTaskReminder: TaskDetailEvent
    data object TaskReminderCanceledSuccessfully: TaskDetailEvent
}