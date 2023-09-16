package com.markusw.dayminder.taskdetail.presentation

import com.markusw.dayminder.core.domain.model.Task

sealed interface TaskDetailEvent {

    data class ChangeTaskTitle(val title: String): TaskDetailEvent
    data class ChangeTaskDescription(val description: String): TaskDetailEvent
    data object SaveChanges: TaskDetailEvent
    data object ChangesAppliedSuccessfully: TaskDetailEvent
    data class CancelTaskReminder(val task: Task): TaskDetailEvent
    data object TaskReminderCanceledSuccessfully: TaskDetailEvent
}