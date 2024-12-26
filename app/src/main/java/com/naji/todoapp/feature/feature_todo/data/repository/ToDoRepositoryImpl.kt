package com.naji.todoapp.feature.feature_todo.data.repository

import com.naji.todoapp.feature.feature_todo.data.data_source.local.dao.ToDoDao
import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import com.naji.todoapp.feature.feature_todo.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val dao: ToDoDao
) : ToDoRepository {

    override fun getAllToDos(): Flow<List<ToDo>> {
        return dao.getAllToDos()
    }

    override suspend fun getToDoById(id: Int): ToDo {
        return dao.getToDoById(id)
    }

    override suspend fun upsertToDo(toDo: ToDo) {
        dao.upsertToDo(toDo)
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        dao.deleteToDo(toDo)
    }
}