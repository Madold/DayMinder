package com.markusw.dayminder.core.domain.model

import com.markusw.dayminder.core.data.model.TaskEntity

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val timestamp: Long,
    val isScheduled: Boolean,
    val importance: Int = IMPORTANCE_NORMAL,
    val isDone: Boolean = false,
) {
    companion object {
        const val IMPORTANCE_HIGH = 1
        const val IMPORTANCE_NORMAL = 0
    }
}

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    timestamp = timestamp,
    isDone = isDone,
    isScheduled = isScheduled,
    importance = importance
)

fun TaskEntity.toDomain() = Task(
    id = id,
    title = title,
    description = description,
    timestamp = timestamp,
    isDone = isDone,
    isScheduled = isScheduled,
    importance = importance
)