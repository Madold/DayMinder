package com.markusw.dayminder.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.markusw.dayminder.addtask.presentation.AddTaskEvent
import com.markusw.dayminder.addtask.presentation.AddTaskScreen
import com.markusw.dayminder.addtask.presentation.AddTaskUiEvent
import com.markusw.dayminder.addtask.presentation.AddTaskViewModel
import com.markusw.dayminder.home.presentation.HomeScreen
import com.markusw.dayminder.home.presentation.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {

        composable(route = Screens.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            HomeScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

        composable(route = Screens.AddTask.route) {
            val viewModel = hiltViewModel<AddTaskViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = viewModel.taskEvents) {
                viewModel.taskEvents.collectLatest { event ->
                    when (event) {
                        is AddTaskEvent.TaskSavedSuccessfully -> {
                            navController.popBackStack()
                        }
                    }
                }
            }

            AddTaskScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

    }

}