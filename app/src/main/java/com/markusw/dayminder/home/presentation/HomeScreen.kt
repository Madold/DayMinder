package com.markusw.dayminder.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dayminder.R
import com.markusw.dayminder.core.presentation.Screens
import com.markusw.dayminder.home.presentation.composables.TaskFilterChip
import com.markusw.dayminder.home.presentation.composables.TaskList

@Composable
fun HomeScreen(
    state: HomeState,
    navController: NavController,
    onEvent: (HomeUiEvent) -> Unit = {},
) {

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SortType.values().forEach { sortType ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TaskFilterChip(
                            onClick = { onEvent(HomeUiEvent.ChangeSortType(sortType)) },
                            isSelected = state.sortType == sortType
                        ) {
                            Text(text = sortType.text.asString())
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddTask.route)
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                val completedTasks = remember(state.taskList) {
                    state.taskList.filter { task -> task.isDone }
                }

                Text(text = stringResource(id = R.string.tasks))

                TaskList(
                    tasks = state.taskList,
                    onEvent = onEvent,
                    modifier = Modifier.fillMaxHeight(0.5f)
                )
                Text(text = stringResource(id = R.string.completed))

                TaskList(
                    tasks = completedTasks,
                    onEvent = onEvent,
                    modifier = Modifier.fillMaxHeight(0.5f)
                )
            }
        }
    )
}
