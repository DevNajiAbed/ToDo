package com.naji.todoapp.feature.feature_todo.presentation.todo_list

import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import com.naji.todoapp.feature.feature_todo.domain.util.OrderType
import com.naji.todoapp.feature.feature_todo.domain.util.ToDoOrder

data class ToDoListState(
    val toDos: List<ToDo> = emptyList(),
    val toDoOrder: ToDoOrder = ToDoOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)