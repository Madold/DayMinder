package com.markusw.dayminder.home.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.markusw.dayminder.home.presentation.HomeUiEvent

@Composable
fun AddTaskDialog(
    taskTitle: String,
    taskDescription: String,
    onEvent: (HomeUiEvent) -> Unit = {},
) {

    Dialog(
        onDismissRequest = {
            onEvent(HomeUiEvent.DismissAddTaskDialog)
        },
        content = {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Add Task")
                    TextField(
                        value = taskTitle,
                        onValueChange = {
                            onEvent(HomeUiEvent.ChangeTaskTitle(it))
                        },
                        label = {
                            Text(text = "Task Title")
                        }
                    )
                    TextField(
                        value = taskDescription,
                        onValueChange = {
                            onEvent(HomeUiEvent.ChangeTaskDescription(it))
                        },
                        label = {
                            Text(text = "Task Description")
                        }
                    )
                    Button(onClick = { onEvent(HomeUiEvent.AddTask) }) {
                        Text(text = "Add Task")
                    }
                }
            }
        }
    )

}