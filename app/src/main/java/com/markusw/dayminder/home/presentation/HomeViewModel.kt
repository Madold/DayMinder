@file:OptIn(ExperimentalCoroutinesApi::class)

package com.markusw.dayminder.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markusw.dayminder.core.domain.model.toDomain
import com.markusw.dayminder.home.domain.use_cases.DeleteTask
import com.markusw.dayminder.home.domain.use_cases.GetTaskOrderedByTimestamp
import com.markusw.dayminder.home.domain.use_cases.GetTaskOrderedByTitle
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
    private val getTaskOrderedByTitle: GetTaskOrderedByTitle,
    private val getTaskOrderedByTimestamp: GetTaskOrderedByTimestamp,
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

            is HomeUiEvent.DeleteTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteTask(event.task)
                }
            }
        }
    }


}