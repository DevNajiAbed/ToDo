package com.naji.todoapp.feature.feature_todo.presentation.todo_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import com.naji.todoapp.feature.feature_todo.domain.use_case.ToDoUseCases
import com.naji.todoapp.feature.feature_todo.domain.util.ToDoOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val toDoUseCases: ToDoUseCases
) : ViewModel() {

    var state by mutableStateOf(ToDoListState())
        private set

    private var getToDosJob: Job? = null

    private var recentlyDeletedToDo: ToDo? = null

    fun onEvent(event: ToDoListEvent) {
        when (event) {
            is ToDoListEvent.ChangeOrder -> {
                if (event.toDoOrder::class == state.toDoOrder::class &&
                    event.toDoOrder.orderType == state.toDoOrder.orderType
                )
                    return
                getToDos(event.toDoOrder)
            }

            is ToDoListEvent.DoneToDo -> {
                viewModelScope.launch {
                    toDoUseCases.upsertToDoUseCase(
                        event.toDo.copy(
                            done = !event.toDo.done
                        )
                    )
                }
            }

            is ToDoListEvent.DeleteToDo -> {
                viewModelScope.launch {
                    toDoUseCases.deleteToDoUseCase(event.toDo)
                    recentlyDeletedToDo = event.toDo
                }
            }

            ToDoListEvent.RestoreToDo -> {
                viewModelScope.launch {
                    toDoUseCases.upsertToDoUseCase(recentlyDeletedToDo ?: return@launch)
                    recentlyDeletedToDo = null
                }
            }

            ToDoListEvent.ToggleOrderSection -> {
                state = state.copy(
                    isOrderSectionVisible = !state.isOrderSectionVisible
                )
            }
        }
    }

    private fun getToDos(toDoOrder: ToDoOrder) {
        getToDosJob?.cancel()
        getToDosJob = viewModelScope.launch {
            toDoUseCases.getAllToDosUseCase(toDoOrder)
                .onEach { toDos ->
                    state = state.copy(
                        toDos = toDos
                    )
                }.launchIn(viewModelScope)
        }
    }
}