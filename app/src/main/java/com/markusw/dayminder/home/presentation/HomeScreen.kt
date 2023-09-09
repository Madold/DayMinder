package com.markusw.dayminder.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markusw.dayminder.home.presentation.composables.AddTaskDialog

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeUiEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SortType.values().forEach { sortType ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            onEvent(HomeUiEvent.ChangeSortType(sortType))
                        }
                    ) {
                        RadioButton(
                            selected = state.sortType == sortType,
                            onClick = {
                                onEvent(HomeUiEvent.ChangeSortType(sortType))
                            }
                        )
                        Text(text = sortType.name)
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(HomeUiEvent.ShowAddTaskDialog) },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                items(state.taskList) { task ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = task.title)
                        IconButton(onClick = { onEvent(HomeUiEvent.DeleteTask(task)) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            if (state.isAddTaskDialogVisible) {
                AddTaskDialog(
                    taskTitle = state.taskTitle,
                    taskDescription = state.taskDescription,
                    onEvent = onEvent
                )
            }
        }
    )
}