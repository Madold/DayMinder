@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("DEPRECATION")

package com.markusw.dayminder.addtask.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dayminder.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.markusw.dayminder.core.presentation.composables.AppButton
import com.markusw.dayminder.core.presentation.composables.ErrorText
import com.markusw.dayminder.core.presentation.composables.TimePickerDialog
import com.markusw.dayminder.core.utils.TimeUtils
import com.markusw.dayminder.ui.theme.DayMinderTheme

@Composable
fun AddTaskScreen(
    state: AddTaskState,
    navController: NavController,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    onEvent: (AddTaskUiEvent) -> Unit = {},
) {

    val systemUiController = rememberSystemUiController()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    val timePickerState = rememberTimePickerState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val primaryColor = MaterialTheme.colorScheme.primary

    val formattedDate by remember {
        derivedStateOf {
            TimeUtils.formatDateFromTimestamp(datePickerState.selectedDateMillis)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            TimeUtils.formatTime(timePickerState.hour, timePickerState.minute)
        }
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = primaryColor,
            darkIcons = true
        )
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
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    onClick = {
                        if (!state.isTaskScheduled) {
                            onEvent(AddTaskUiEvent.SaveTask)
                            return@AppButton
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                return@AppButton
                            }
                        }

                        onEvent(AddTaskUiEvent.SaveTask)
                    },
                    content = {
                        Text(text = stringResource(id = R.string.create_task))
                    }
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 15.dp,
                                bottomEnd = 15.dp
                            )
                        )
                        .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.task_title),
                        color = MaterialTheme.colorScheme.surface
                    )
                    TextInputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.taskTitle,
                        onValueChange = {
                            onEvent(AddTaskUiEvent.ChangeTaskTitle(it))
                        },
                        isError = state.taskTitleError != null,
                    )
                    AnimatedVisibility(visible = state.taskTitleError != null) {
                        ErrorText(text = state.taskTitleError?.asString())
                    }
                    Text(
                        text = stringResource(id = R.string.task_description),
                        color = MaterialTheme.colorScheme.surface
                    )
                    TextInputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.taskDescription,
                        onValueChange = {
                            onEvent(AddTaskUiEvent.ChangeTaskDescription(it))
                        },
                        maxLines = 5
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            checked = state.isTaskImportant,
                            onCheckedChange = {
                                onEvent(AddTaskUiEvent.ChangeTaskImportance(it))
                            }
                        )
                        Text(stringResource(id = R.string.mark_as_important))
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            checked = state.isTaskScheduled,
                            onCheckedChange = {
                                onEvent(AddTaskUiEvent.ChangeTaskScheduled(it))
                            }
                        )
                        Text(stringResource(id = R.string.remember))
                    }
                    AnimatedVisibility(visible = state.isTaskScheduled) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = stringResource(id = R.string.date))
                                OutlinedButton(onClick = { onEvent(AddTaskUiEvent.ShowDatePicker) }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = null
                                    )
                                    AnimatedVisibility(
                                        visible = formattedDate.isNotEmpty(),
                                        enter = fadeIn(
                                            animationSpec = tween(
                                                delayMillis = 200,
                                                durationMillis = 500
                                            )
                                        ) + expandHorizontally(
                                            animationSpec = tween(
                                                delayMillis = 200,
                                                durationMillis = 500
                                            )
                                        )
                                    ) {
                                        Text(text = formattedDate)
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = stringResource(id = R.string.time))
                                OutlinedButton(onClick = { onEvent(AddTaskUiEvent.ShowTimePicker) }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_clock),
                                        contentDescription = null
                                    )
                                    Text(text = formattedTime)
                                }
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
                        onEvent(
                            AddTaskUiEvent.ChangeSelectedDate(
                                datePickerState.selectedDateMillis!!
                            )
                        )
                        onEvent(AddTaskUiEvent.HideDatePicker)
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text(stringResource(id = R.string.OK))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onEvent(AddTaskUiEvent.HideDatePicker) }
                ) {
                    Text(stringResource(id = R.string.cancel))
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
                onEvent(
                    AddTaskUiEvent.ChangeSelectedHour(
                        timePickerState.hour
                    )
                )
                onEvent(
                    AddTaskUiEvent.ChangeSelectedMinute(
                        timePickerState.minute
                    )
                )
                onEvent(AddTaskUiEvent.HideTimePicker)
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

@Composable
private fun TextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    maxLines: Int = Int.MAX_VALUE
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(15.dp),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.surface
        ),
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    DayMinderTheme {
        AddTaskScreen(
            state = AddTaskState(),
            navController = rememberNavController(),
            permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {

            }
        )
    }
}