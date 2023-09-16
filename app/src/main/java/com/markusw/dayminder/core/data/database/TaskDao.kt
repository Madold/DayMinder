package com.markusw.dayminder.core.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.markusw.dayminder.core.Constants.TASK_TABLE_NAME
import com.markusw.dayminder.core.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Upsert
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM $TASK_TABLE_NAME ORDER BY importance DESC, title ASC")
    fun getDailyTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE importance = 1 ORDER BY title ASC")
    fun getImportantTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity

}