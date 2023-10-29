package com.markusw.dayminder.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.markusw.dayminder.core.utils.Constants.TASK_TABLE_NAME

@Entity(tableName = TASK_TABLE_NAME)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val timestamp: Long,
    val isDone: Boolean,
    val isScheduled: Boolean,
    val importance: Int,
    val notificationId: Int? = null
)
