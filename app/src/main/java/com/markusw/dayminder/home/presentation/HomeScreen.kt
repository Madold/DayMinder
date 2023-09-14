@file:OptIn(ExperimentalMaterial3Api::class)

package com.markusw.dayminder.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dayminder.R
import com.markusw.dayminder.core.presentation.Screens
import com.markusw.dayminder.home.presentation.composables.TaskFilterChip
import com.markusw.dayminder.home.presentation.composables.TaskItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    state: HomeState,
    navController: NavController,
    onEvent: (HomeUiEvent) -> Unit = {},
) {

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SortType.values().forEach { sortType ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TaskFilterChip(
                            onClick = { onEvent(HomeUiEvent.ChangeSortType(sortType)) },
                            isSelected = state.sortType == sortType
                        ) {
                            Text(text = sortType.name)
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
            LazyColumn(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    state.taskList,
                    key = { task -> task.id }
                ) { task ->
                    var isExpanded by remember { mutableStateOf(true) }
                    val dismissState = rememberDismissState(
                        confirmValueChange = { dismissValue ->

                            if (dismissValue == DismissValue.DismissedToStart) {
                                return@rememberDismissState false
                            }

                            coroutineScope.launch {
                                if (dismissValue == DismissValue.DismissedToEnd) {
                                    isExpanded = false
                                    delay(350)
                                    onEvent(HomeUiEvent.DeleteTask(task))
                                }
                            }
                            true
                        },
                    )

                    val swipeBackground = when (dismissState.dismissDirection) {
                        DismissDirection.StartToEnd -> Color(0xFFFF1744)
                        else -> Color.Transparent
                    }

                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = fadeIn(),
                        exit = shrinkVertically(
                            animationSpec = tween(
                                durationMillis = 350
                            )
                        )
                    ) {
                        SwipeToDismiss(
                            state = dismissState,
                            background = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 0.dp,
                                                topEnd = 8.dp,
                                                bottomEnd = 8.dp,
                                                bottomStart = 0.dp
                                            )
                                        )
                                        .background(swipeBackground)
                                    ,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_trash_can),
                                        contentDescription = null
                                    )
                                }
                            },
                            directions = setOf(DismissDirection.StartToEnd),
                            dismissContent = {
                                TaskItem(
                                    task = task,
                                    onClick = { },
                                    onToggleClick = { }
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}