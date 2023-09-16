package com.markusw.dayminder.addtask.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markusw.dayminder.addtask.domain.NotificationItem
import com.markusw.dayminder.addtask.domain.NotificationSchedulerService
import com.markusw.dayminder.addtask.domain.use_cases.InsertTask
import com.markusw.dayminder.core.domain.model.Task
import com.markusw.dayminder.core.domain.use_cases.ValidateTaskTitle
import com.markusw.dayminder.core.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val insertTask: InsertTask,
    private val validateTaskTitle: ValidateTaskTitle,
    private val notificationSchedulerService: NotificationSchedulerService
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskState())
    val uiState = _uiState.asStateFlow()
    private val taskEventChannel = Channel<AddTaskEvent>()
    val taskEvents = taskEventChannel.receiveAsFlow()
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun onEvent(event: AddTaskUiEvent) {
        when (event) {
            is AddTaskUiEvent.ChangeTaskDescription -> {
                _uiState.update { it.copy(taskDescription = event.taskDescription) }
            }

            is AddTaskUiEvent.ChangeTaskTitle -> {
                _uiState.update { it.copy(taskTitle = event.taskTitle) }
            }

            is AddTaskUiEvent.SaveTask -> handleTaskCreation()

            is AddTaskUiEvent.HideDatePicker -> {
                _uiState.update { it.copy(isDatePickerVisible = false) }
            }

            is AddTaskUiEvent.HideTimePicker -> {
                _uiState.update { it.copy(isTimePickerVisible = false) }
            }

            is AddTaskUiEvent.ShowDatePicker -> {
                _uiState.update { it.copy(isDatePickerVisible = true) }
            }

            is AddTaskUiEvent.ShowTimePicker -> {
                _uiState.update { it.copy(isTimePickerVisible = true) }
            }

            is AddTaskUiEvent.ChangeSelectedDate -> {
                _uiState.update { it.copy(selectedDateInMillis = event.selectedDateInMillis) }
            }

            is AddTaskUiEvent.ChangeSelectedHour -> {
                _uiState.update { it.copy(selectedHour = event.selectedHour) }
            }

            is AddTaskUiEvent.ChangeSelectedMinute -> {
                _uiState.update { it.copy(selectedMinute = event.selectedMinute) }
            }

            is AddTaskUiEvent.ChangeTaskScheduled -> {
                _uiState.update { it.copy(isTaskScheduled = event.scheduled) }
            }

            is AddTaskUiEvent.ChangeTaskImportance -> {
                _uiState.update { it.copy(isTaskImportant = event.isImportant) }
            }
        }
    }

    private fun resetTaskFields() {
        _uiState.update {
            it.copy(
                taskTitle = "",
                taskDescription = "",
                taskTitleError = null,
            )
        }
    }

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(permission: String, isGranted: Boolean) {
        if (!isGranted) {
            visiblePermissionDialogQueue.add(permission)
        }
    }

    private fun handleTaskCreation() {
        val taskTitle = _uiState.value.taskTitle
        val taskDescription = _uiState.value.taskDescription
        val taskTitleValidationResult = validateTaskTitle(taskTitle)
        val isTaskScheduled = _uiState.value.isTaskScheduled
        val isImportant = _uiState.value.isTaskImportant

        if (!taskTitleValidationResult.success) {
            _uiState.update {
                it.copy(
                    taskTitleError = taskTitleValidationResult.errorMessage,
                )
            }
            return
        }

        if (isTaskScheduled) {
            val endDateTimestamp = _uiState.value.selectedDateInMillis
            val hour = _uiState.value.selectedHour
            val minute = _uiState.value.selectedMinute

            notificationSchedulerService.scheduleNotification(
                NotificationItem(
                    id = UUID.randomUUID().hashCode(),
                    title = taskTitle,
                    message = taskDescription,
                    timestamp = TimeUtils.computeTimeStamp(
                        endDateTimestamp,
                        hour,
                        minute
                    )
                )
            )
        }

        resetTaskFields()

        viewModelScope.launch(Dispatchers.IO) {
            insertTask(
                Task(
                    title = taskTitle,
                    description = taskDescription,
                    timestamp = TimeUtils.getDeviceHourInTimestamp(),
                    isDone = false,
                    isScheduled = isTaskScheduled,
                    importance = if (isImportant) Task.IMPORTANCE_HIGH else Task.IMPORTANCE_NORMAL
                )
            )
            taskEventChannel.send(AddTaskEvent.TaskSavedSuccessfully)
        }
    }

    override fun onCleared() {
        super.onCleared()
        taskEventChannel.close()
    }

}