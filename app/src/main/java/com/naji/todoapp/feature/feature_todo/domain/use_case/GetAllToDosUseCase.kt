package com.naji.todoapp.feature.feature_todo.domain.use_case

import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import com.naji.todoapp.feature.feature_todo.domain.repository.ToDoRepository
import com.naji.todoapp.feature.feature_todo.domain.util.OrderType
import com.naji.todoapp.feature.feature_todo.domain.util.ToDoOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllToDosUseCase @Inject constructor(
    private val repository: ToDoRepository
) {

    operator fun invoke(
        toDoOrder: ToDoOrder = ToDoOrder.Date(OrderType.Descending)
    ): Flow<List<ToDo>> {
        return repository.getAllToDos()
            .map { toDos ->
                when (toDoOrder) {
                    is ToDoOrder.Date -> {
                        when (toDoOrder.orderType) {
                            OrderType.Ascending -> toDos.sortedBy { it.timestamp }
                            OrderType.Descending -> toDos.sortedByDescending { it.timestamp }
                        }
                    }

                    is ToDoOrder.Title -> {
                        when (toDoOrder.orderType) {
                            OrderType.Ascending -> toDos.sortedBy { it.title }
                            OrderType.Descending -> toDos.sortedByDescending { it.title }
                        }
                    }

                    is ToDoOrder.Description -> {
                        when (toDoOrder.orderType) {
                            OrderType.Ascending -> toDos.sortedBy { it.description }
                            OrderType.Descending -> toDos.sortedByDescending { it.description }
                        }
                    }
                }
            }
    }
}