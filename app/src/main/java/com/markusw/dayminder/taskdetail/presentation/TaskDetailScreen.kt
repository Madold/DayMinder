@file:OptIn(ExperimentalMaterial3Api::class)

package com.markusw.dayminder.taskdetail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.markusw.dayminder.R
import com.markusw.dayminder.core.presentation.composables.AppButton
import com.markusw.dayminder.core.presentation.composables.AppDialog
import com.markusw.dayminder.core.presentation.composables.OutlinedAppButton
import com.markusw.dayminder.core.presentation.composables.TransparentTextField

@Composable
fun TaskDetailScreen(
    state: TaskDetailState,
    onEvent: (TaskDetailEvent) -> Unit,
    navController: NavController = rememberNavController()
) {

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.task_detail))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(TaskDetailEvent.SaveChanges)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = null
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TransparentTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.selectedTask?.title ?: "",
                    onValueChange = {
                        onEvent(TaskDetailEvent.ChangeTaskTitle(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.task_title), style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface))
                    },
                    textStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                    errorText = state.taskTitleError?.asString(),
                    isError = state.taskTitleError != null
                )
                TransparentTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.selectedTask?.description ?: "",
                    onValueChange = {
                        onEvent(TaskDetailEvent.ChangeTaskDescription(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.task_description), style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface))
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
                )

                state.selectedTask?.let {
                    if (it.isScheduled) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            AppButton(
                                onClick = { onEvent(TaskDetailEvent.ShowTaskReminderCancelDialog) }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_cancel_alarm),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = stringResource(id = R.string.cancel_reminder))
                            }
                        }
                    }
                }
            }

            if (state.isCancelReminderDialogVisible) {
                AppDialog(
                    onDismissRequest = {
                        onEvent(TaskDetailEvent.HideTaskReminderCancelDialog)
                    },
                    title = {
                        Text(
                            text = stringResource(id = R.string.cancel_reminder),
                            style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface)
                        )
                    },
                    buttons = {
                        OutlinedAppButton(
                            onClick = {
                                onEvent(TaskDetailEvent.HideTaskReminderCancelDialog)
                            }
                        ) {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        AppButton(
                            onClick = {
                                onEvent(TaskDetailEvent.CancelTaskReminder)
                                onEvent(TaskDetailEvent.HideTaskReminderCancelDialog)
                            }
                        ) {
                            Text(text = stringResource(id = R.string.yes))
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.cancel_reminder_message))
                }
            }

        }
    )

}