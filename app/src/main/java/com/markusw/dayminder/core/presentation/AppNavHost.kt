package com.markusw.dayminder.core.presentation

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.markusw.dayminder.addtask.presentation.AddTaskEvent
import com.markusw.dayminder.addtask.presentation.AddTaskScreen
import com.markusw.dayminder.addtask.presentation.AddTaskViewModel
import com.markusw.dayminder.core.ext.openAppSettings
import com.markusw.dayminder.core.presentation.composables.PermissionDialog
import com.markusw.dayminder.home.presentation.HomeScreen
import com.markusw.dayminder.home.presentation.HomeViewModel
import com.markusw.dayminder.taskdetail.presentation.TaskDetailScreen
import com.markusw.dayminder.taskdetail.presentation.TaskDetailEvent
import com.markusw.dayminder.taskdetail.presentation.TaskDetailViewModel
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
            val context = LocalContext.current
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        viewModel.onPermissionResult(
                            permission = Manifest.permission.POST_NOTIFICATIONS,
                            isGranted = isGranted
                        )
                    }
                }
            )

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
                navController = navController,
                permissionLauncher = permissionLauncher
            )

            viewModel.visiblePermissionDialogQueue
                .asReversed()
                .forEach { permission ->
                    PermissionDialog(
                        permissionTextProvider = when (permission) {
                            Manifest.permission.POST_NOTIFICATIONS -> {
                                PostNotificationsPermissionProvider()
                            }

                            else -> return@forEach
                        },
                        isPermanentlyDeclined = (context as Activity).shouldShowRequestPermissionRationale(
                            permission
                        ).not(),
                        onDismiss = viewModel::dismissDialog,
                        onDone = {
                            viewModel.dismissDialog()
                            permissionLauncher.launch(permission)
                        },
                        onGotToSettings = {
                            context.openAppSettings()
                            viewModel.dismissDialog()
                        }
                    )
                }
        }

        composable(
            route = "${Screens.TaskDetail.route}/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            val viewModel = hiltViewModel<TaskDetailViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = viewModel.taskDetailEvents) {
                viewModel.taskDetailEvents.collectLatest { event ->
                    when (event) {
                        is TaskDetailEvent.ChangesAppliedSuccessfully -> {
                            navController.popBackStack()
                        }
                        else -> return@collectLatest
                    }
                }
            }

            TaskDetailScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )

        }

    }

}