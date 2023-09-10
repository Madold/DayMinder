package com.markusw.dayminder.core.presentation

sealed class Screens(
    val route: String
) {
    data object Home : Screens(
        route = "home"
    )
    data object AddTask : Screens(
        route = "add_task"
    )

}