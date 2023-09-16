@file:OptIn(ExperimentalMaterial3Api::class)

package com.markusw.dayminder.taskdetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dayminder.R

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
                    .verticalScroll(scrollState)
            ) {
                BasicTextField(
                    value = state.selectedTask?.title ?: "",
                    onValueChange = {
                        onEvent(TaskDetailEvent.ChangeTaskTitle(it))
                    }
                )
                BasicTextField(
                    value = state.selectedTask?.description ?: "",
                    onValueChange = {
                        onEvent(TaskDetailEvent.ChangeTaskDescription(it))
                    }
                )
            }
        }
    )

}