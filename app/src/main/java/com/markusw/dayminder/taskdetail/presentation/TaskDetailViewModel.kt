package com.markusw.dayminder.taskdetail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markusw.dayminder.core.domain.NotificationItem
import com.markusw.dayminder.core.domain.model.toDomain
import com.markusw.dayminder.core.domain.use_cases.InsertTask
import com.markusw.dayminder.taskdetail.domain.use_cases.CancelTaskReminder
import com.markusw.dayminder.taskdetail.domain.use_cases.GetTaskById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val getTaskById: GetTaskById,
    private val insertTask: InsertTask,
    private val cancelTaskReminder: CancelTaskReminder,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailState())
    val uiState = _uiState.asStateFlow()
    private val taskDetailChannel = Channel<TaskDetailEvent>()
    val taskDetailEvents = taskDetailChannel.receiveAsFlow()

    init {
        savedStateHandle.get<Int>("id")?.let { taskId ->
            viewModelScope.launch(Dispatchers.IO) {
                val task = getTaskById(taskId)
                _uiState.update {
                    it.copy(
                        selectedTask = task.toDomain()
                    )
                }
            }
        }
    }

    fun onEvent(event: TaskDetailEvent) {
        when (event) {
            is TaskDetailEvent.ChangeTaskDescription -> {
                _uiState.update {
                    it.copy(
                        selectedTask = it.selectedTask?.copy(
                            description = event.description
                        )
                    )
                }
            }

            is TaskDetailEvent.ChangeTaskTitle -> {
                _uiState.update {
                    it.copy(
                        selectedTask = it.selectedTask?.copy(
                            title = event.title
                        )
                    )
                }
            }

            is TaskDetailEvent.SaveChanges -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.value.selectedTask?.let { task ->
                        insertTask(task)
                        taskDetailChannel.send(TaskDetailEvent.ChangesAppliedSuccessfully)
                    }
                }
            }

            is TaskDetailEvent.CancelTaskReminder -> {
                val task = _uiState.value.selectedTask ?: return

                task.notificationId?.let { id ->
                    cancelTaskReminder(
                        NotificationItem(
                            id = id,
                            title = "",
                            message = "",
                            timestamp = 0L
                        )
                    )
                }

                task.copy(
                    isScheduled = false,
                    notificationId = null,
                    timestamp = 0L
                ).also {
                    viewModelScope.launch(Dispatchers.IO) {
                        insertTask(it)
                        taskDetailChannel.send(TaskDetailEvent.TaskReminderCanceledSuccessfully)
                    }
                }
            }

            else -> return
        }
    }

    override fun onCleared() {
        super.onCleared()
        taskDetailChannel.close()
    }

}