@file:OptIn(ExperimentalCoroutinesApi::class)

package com.markusw.dayminder.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markusw.dayminder.core.utils.TimeUtils
import com.markusw.dayminder.core.domain.Task
import com.markusw.dayminder.core.domain.toDomain
import com.markusw.dayminder.home.domain.use_cases.DeleteTask
import com.markusw.dayminder.home.domain.use_cases.GetTaskOrderedByTimestamp
import com.markusw.dayminder.home.domain.use_cases.GetTaskOrderedByTitle
import com.markusw.dayminder.home.domain.use_cases.InsertTask
import com.markusw.dayminder.home.domain.use_cases.ValidateTaskTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deleteTask: DeleteTask,
    private val insertTask: InsertTask,
    private val getTaskOrderedByTitle: GetTaskOrderedByTitle,
    private val getTaskOrderedByTimestamp: GetTaskOrderedByTimestamp,
    private val validateTaskTitle: ValidateTaskTitle
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    private val _sortType = MutableStateFlow(SortType.Title)
    private val _tasks = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.Title -> getTaskOrderedByTitle()
            SortType.Timestamp -> getTaskOrderedByTimestamp()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val uiState = combine(_uiState, _sortType, _tasks) { uiState, sortType, tasks ->
        uiState.copy(
            taskList = tasks.map { it.toDomain() },
            sortType = sortType,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeState())

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ChangeSortType -> {
                _sortType.update { event.sortType }
            }

            is HomeUiEvent.ShowAddTaskDialog -> {
                _uiState.update { it.copy(isAddTaskDialogVisible = true) }
            }

            is HomeUiEvent.ChangeTaskDescription -> {
                _uiState.update { it.copy(taskDescription = event.description) }
            }

            is HomeUiEvent.ChangeTaskTitle -> {
                _uiState.update { it.copy(taskTitle = event.title) }
            }

            is HomeUiEvent.AddTask -> {
                val title = _uiState.value.taskTitle
                val description = _uiState.value.taskDescription
                val titleValidationResult = validateTaskTitle(title)

                if (!titleValidationResult.success) {
                    _uiState.update {
                        it.copy(
                            taskTitleError = titleValidationResult.errorMessage
                        )
                    }
                    return
                }

                resetTaskFields()

                viewModelScope.launch(Dispatchers.IO) {
                    insertTask(
                        Task(
                            title = title,
                            description = description,
                            timestamp = TimeUtils.getDeviceHourInTimestamp()
                        )
                    )
                }
            }

            is HomeUiEvent.DismissAddTaskDialog -> {
                _uiState.update { it.copy(isAddTaskDialogVisible = false) }
            }

            is HomeUiEvent.DeleteTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteTask(event.task)
                }
            }
        }
    }

    private fun resetTaskFields() {
        _uiState.update {
            it.copy(
                taskTitle = "",
                taskDescription = "",
                isAddTaskDialogVisible = false,
            )
        }
    }

}