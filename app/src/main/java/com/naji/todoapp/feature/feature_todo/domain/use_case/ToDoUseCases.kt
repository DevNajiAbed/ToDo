package com.naji.todoapp.feature.feature_todo.domain.use_case

data class ToDoUseCases(
    val getAllToDosUseCase: GetAllToDosUseCase,
    val getToDoByIdUseCase: GetToDoByIdUseCase,
    val upsertToDoUseCase: UpsertToDoUseCase,
    val deleteToDoUseCase: DeleteToDoUseCase
)
