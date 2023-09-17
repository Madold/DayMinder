package com.markusw.dayminder.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
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

                val unCompletedTasks = remember(state.taskList) {
                    state.taskList.filter { task -> !task.isDone }
                }
                val unCompletedTasksCount by remember(unCompletedTasks) {
                    derivedStateOf { unCompletedTasks.size }
                }
                val completedTasks = remember(state.taskList) {
                    state.taskList.filter { task -> task.isDone }
                }
                val completedTasksCount by remember(completedTasks) {
                    derivedStateOf { completedTasks.size }
                }

                Text(text = "${stringResource(id = R.string.tasks)} (${unCompletedTasksCount})")

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    AnimatedVisibility(
                        unCompletedTasks.isEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        TaskListEmptyAnimation()
                    }
                    TaskList(
                        tasks = unCompletedTasks,
                        onEvent = onEvent,
                        modifier = Modifier.fillMaxHeight(),
                        onItemClick = { task ->
                            navController.navigate("${Screens.TaskDetail.route}/${task.id}")
                        }
                    )

                }

                Text(text = "${stringResource(id = R.string.completed)} (${completedTasksCount})")

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    AnimatedVisibility(
                        completedTasks.isEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        TaskListDoneAnimation()
                    }
                    TaskList(
                        tasks = completedTasks,
                        onEvent = onEvent,
                        modifier = Modifier.fillMaxHeight(),
                        onItemClick = { task ->
                            navController.navigate("${Screens.TaskDetail.route}/${task.id}")
                        }
                    )

                }
            }
        }
    )
}

@Composable
private fun TaskListDoneAnimation() {
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.task_list_done_anim))
    val progress by animateLottieCompositionAsState(composition = lottieComposition)

    LottieAnimation(
        composition = lottieComposition,
        progress = { progress }
    )

}

@Composable
private fun TaskListEmptyAnimation() {
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.task_list_empty_anim))
    val progress by animateLottieCompositionAsState(
        composition = lottieComposition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = lottieComposition,
        progress = { progress }
    )
}