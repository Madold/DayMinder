package com.markusw.dayminder.home.presentation

import com.markusw.dayminder.core.domain.model.Task

data class HomeState(
    val taskList: List<Task> = listOf(),
    val sortType: SortType = SortType.MyDay,
)
