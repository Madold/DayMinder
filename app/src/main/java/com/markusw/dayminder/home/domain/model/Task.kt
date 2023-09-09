package com.markusw.dayminder.home.domain.model

import com.markusw.dayminder.home.data.model.TaskEntity

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val timestamp: Long,
)

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    timestamp = timestamp
)

fun TaskEntity.toDomain() = Task(
    id = id,
    title = title,
    description = description,
    timestamp = timestamp
)