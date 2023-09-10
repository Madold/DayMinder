package com.markusw.dayminder.addtask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markusw.dayminder.addtask.domain.use_cases.InsertTask
import com.markusw.dayminder.core.domain.model.Task
import com.markusw.dayminder.core.domain.use_cases.ValidateTaskTitle
import com.markusw.dayminder.core.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val insertTask: InsertTask,
    private val validateTaskTitle: ValidateTaskTitle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskState())
    val uiState = _uiState.asStateFlow()
    private val taskEventChannel = Channel<AddTaskEvent>()
    val taskEvents = taskEventChannel.receiveAsFlow()

    fun onEvent(event: AddTaskUiEvent) {
        when(event) {
            is AddTaskUiEvent.ChangeTaskDescription -> {
                _uiState.update { it.copy(taskDescription = event.taskDescription) }
            }

            is AddTaskUiEvent.ChangeTaskTitle -> {
                _uiState.update { it.copy(taskTitle = event.taskTitle) }
            }

            is AddTaskUiEvent.SaveTask -> {
                val taskTitle = _uiState.value.taskTitle
                val taskDescription = _uiState.value.taskDescription
                val taskTitleValidationResult = validateTaskTitle(taskTitle)

                if (!taskTitleValidationResult.success) {
                    _uiState.update {
                        it.copy(
                            taskTitleError = taskTitleValidationResult.errorMessage,
                        )
                    }
                    return
                }

                resetTaskFields()

                viewModelScope.launch(Dispatchers.IO) {
                    insertTask(
                        Task(
                            title = taskTitle,
                            description = taskDescription,
                            timestamp = TimeUtils.getDeviceHourInTimestamp()
                        )
                    )
                    taskEventChannel.send(AddTaskEvent.TaskSavedSuccessfully)
                }

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

    override fun onCleared() {
        super.onCleared()
        taskEventChannel.close()
    }

}