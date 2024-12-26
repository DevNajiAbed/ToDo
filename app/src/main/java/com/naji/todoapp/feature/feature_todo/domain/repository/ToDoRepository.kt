package com.naji.todoapp.feature.feature_todo.domain.repository

import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getAllToDos(): Flow<List<ToDo>>

    suspend fun getToDoById(id: Int): ToDo

    suspend fun upsertToDo(toDo: ToDo)

    suspend fun deleteToDo(toDo: ToDo)
}