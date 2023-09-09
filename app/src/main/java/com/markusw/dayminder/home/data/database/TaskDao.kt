package com.markusw.dayminder.home.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.markusw.dayminder.core.Constants.TASK_TABLE_NAME
import com.markusw.dayminder.home.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Upsert
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM $TASK_TABLE_NAME ORDER BY timestamp ASC")
    fun getTaskOrderedByTimestamp(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM $TASK_TABLE_NAME ORDER BY title ASC")
    fun getTaskOrderedByTitle(): Flow<List<TaskEntity>>


}