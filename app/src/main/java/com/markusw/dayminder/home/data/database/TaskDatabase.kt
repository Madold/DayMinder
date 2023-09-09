package com.markusw.dayminder.home.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.markusw.dayminder.home.data.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TaskDatabase: RoomDatabase() {
    abstract val dao: TaskDao
}