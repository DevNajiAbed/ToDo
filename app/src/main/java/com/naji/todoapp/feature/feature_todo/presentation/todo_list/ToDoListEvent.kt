package com.naji.todoapp.feature.feature_todo.presentation.todo_list

import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import com.naji.todoapp.feature.feature_todo.domain.util.ToDoOrder

sealed class ToDoListEvent {
    data class ChangeOrder(val toDoOrder: ToDoOrder) : ToDoListEvent()
    data class DoneToDo(val toDo: ToDo) : ToDoListEvent()
    data class DeleteToDo(val toDo: ToDo) : ToDoListEvent()
    data object RestoreToDo : ToDoListEvent()
    data object ToggleOrderSection : ToDoListEvent()
}