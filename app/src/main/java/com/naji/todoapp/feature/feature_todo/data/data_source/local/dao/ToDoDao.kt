package com.naji.todoapp.feature.feature_todo.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo")
    fun getAllToDos(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getToDoById(id: Int): ToDo

    @Upsert
    suspend fun upsertToDo(toDo: ToDo)

    @Delete
    suspend fun deleteToDo(toDo: ToDo)
}