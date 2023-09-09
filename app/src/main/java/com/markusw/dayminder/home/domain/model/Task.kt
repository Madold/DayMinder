package com.markusw.dayminder.home.domain.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val timestamp: Long,
)
