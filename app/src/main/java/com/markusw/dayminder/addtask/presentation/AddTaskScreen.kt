@file:OptIn(ExperimentalMaterial3Api::class)

package com.markusw.dayminder.addtask.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dayminder.R
import com.markusw.dayminder.core.presentation.composables.TimePickerDialog
import com.markusw.dayminder.core.utils.TimeUtils

private const val ONE_DAY = 86400000L

@Composable
fun AddTaskScreen(
    state: AddTaskState,
    navController: NavController,
    onEvent: (AddTaskUiEvent) -> Unit = {},
) {

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    val scrollState = rememberScrollState()
    val formattedDate = remember(datePickerState.selectedDateMillis) {
        TimeUtils.formatDateFromTimestamp(datePickerState.selectedDateMillis?.plus(ONE_DAY))
    }
    val formattedTime = remember(timePickerState.hour, timePickerState.minute) {
        TimeUtils.formatTime(timePickerState.hour, timePickerState.minute)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.add_task))
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
        bottomBar = {
            Button(onClick = { onEvent(AddTaskUiEvent.SaveTask) }) {
                Text(text = stringResource(id = R.string.create_task))
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = stringResource(id = R.string.task_title))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.taskTitle,
                    onValueChange = {
                        onEvent(AddTaskUiEvent.ChangeTaskTitle(it))
                    },
                    isError = state.taskTitleError != null,
                )
                AnimatedVisibility(visible = state.taskTitleError != null) {
                    state.taskTitleError?.let { titleError ->
                        Text(text = titleError.asString())
                    }
                }
                Text(text = stringResource(id = R.string.task_description))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.taskDescription,
                    onValueChange = {
                        onEvent(AddTaskUiEvent.ChangeTaskDescription(it))
                    },
                    maxLines = 5
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Date")
                        OutlinedButton(onClick = { onEvent(AddTaskUiEvent.ShowDatePicker) }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null
                            )
                            AnimatedVisibility(visible = formattedDate.isNotEmpty()) {
                                Text(text = formattedDate)
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Time")
                        OutlinedButton(onClick = { onEvent(AddTaskUiEvent.ShowTimePicker) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_clock),
                                contentDescription = null
                            )
                            AnimatedVisibility(visible = formattedTime.isNotEmpty()) {
                                Text(text = formattedTime)
                            }
                        }
                    }
                }
            }

        }
    )

    if (state.isDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { onEvent(AddTaskUiEvent.HideDatePicker) },
            confirmButton = {
                TextButton(
                    onClick = {
                        println("Selected date timestamp: ${datePickerState.selectedDateMillis?.plus(
                            ONE_DAY)}")
                        onEvent(
                            AddTaskUiEvent.ChangeSelectedDate(
                                datePickerState.selectedDateMillis?.plus(ONE_DAY)
                            )
                        )
                        onEvent(AddTaskUiEvent.HideDatePicker)
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onEvent(AddTaskUiEvent.HideDatePicker) }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (state.isTimePickerVisible) {
        TimePickerDialog(
            state = timePickerState,
            onConfirmButtonClick = {
                onEvent(AddTaskUiEvent.HideTimePicker)
                println("Selected time: ${timePickerState.hour} : ${timePickerState.minute}")
            },
            onDismissButtonClick = {
                onEvent(AddTaskUiEvent.HideTimePicker)
            },
            onDismissRequest = {
                onEvent(AddTaskUiEvent.HideTimePicker)
            }
        )
    }


}