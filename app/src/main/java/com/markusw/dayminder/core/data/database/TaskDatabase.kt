package com.markusw.dayminder.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.markusw.dayminder.core.data.database.TaskDao
import com.markusw.dayminder.core.data.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TaskDatabase: RoomDatabase() {
    abstract val dao: TaskDao
}