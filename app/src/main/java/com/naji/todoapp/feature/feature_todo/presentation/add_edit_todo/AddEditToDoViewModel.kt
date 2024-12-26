package com.naji.todoapp.feature.feature_todo.presentation.add_edit_todo

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import com.naji.todoapp.feature.feature_todo.domain.use_case.ToDoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditToDoViewModel @Inject constructor(
    private val toDoUseCases: ToDoUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(AddEditToDoState())
        private set

    private var toDoId: Int? = null

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        savedStateHandle.get<Int>(ToDo.PARAM_ID)?.let { id ->
            viewModelScope.launch {
                toDoUseCases.getToDoByIdUseCase(id)
                    .also { toDo ->
                        state = state.copy(
                            color = toDo.color,
                            title = toDo.title,
                            description = toDo.description
                        )
                        toDoId = id
                    }
            }
        }
    }

    fun onEvent(event: AddEditToDoEvent) {
        when (event) {
            is AddEditToDoEvent.ChangeColor -> {
                state = state.copy(
                    color = event.color
                )
            }

            is AddEditToDoEvent.ChangeTitle -> {
                state = state.copy(
                    title = event.title
                )
            }

            is AddEditToDoEvent.ChangeDescription -> {
                state = state.copy(
                    description = event.description
                )
            }

            AddEditToDoEvent.SaveToDo -> {
                viewModelScope.launch {
                    toDoUseCases.upsertToDoUseCase(
                        ToDo(
                            title = state.title,
                            description = state.description,
                            color = state.color,
                            done = false,
                            timestamp = System.currentTimeMillis(),
                            id = toDoId
                        )
                    )
                }
            }
        }
    }
}