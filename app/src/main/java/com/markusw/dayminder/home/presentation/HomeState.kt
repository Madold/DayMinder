package com.markusw.dayminder.home.presentation

import com.markusw.dayminder.core.domain.model.Task
import com.markusw.dayminder.core.presentation.UiText

data class HomeState(
    val taskList: List<Task> = listOf(),
    val sortType: SortType = SortType.Title,
)
