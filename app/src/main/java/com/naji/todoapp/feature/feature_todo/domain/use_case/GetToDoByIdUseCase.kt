package com.naji.todoapp.feature.feature_todo.domain.use_case

import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import com.naji.todoapp.feature.feature_todo.domain.repository.ToDoRepository
import javax.inject.Inject

class GetToDoByIdUseCase @Inject constructor(
    private val repository: ToDoRepository
) {

    suspend operator fun invoke(id: Int): ToDo {
        return repository.getToDoById(id)
    }
}