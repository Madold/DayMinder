@file:OptIn(ExperimentalMaterial3Api::class)

package com.markusw.dayminder.addtask.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import com.example.dayminder.R

@Composable
fun AddTaskScreen(
    state: AddTaskState,
    navController: NavController,
    onEvent: (AddTaskUiEvent) -> Unit = {},
) {

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
                modifier = Modifier.padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = stringResource(id = R.string.task_title))
                OutlinedTextField(
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
                    value = state.taskDescription,
                    onValueChange = {
                        onEvent(AddTaskUiEvent.ChangeTaskDescription(it))
                    }
                )
            }
        }
    )
}