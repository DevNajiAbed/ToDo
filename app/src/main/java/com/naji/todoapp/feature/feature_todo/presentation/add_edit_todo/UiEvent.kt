package com.naji.todoapp.feature.feature_todo.presentation.add_edit_todo

sealed class UiEvent {
    data object NavigateUp : UiEvent()
    data class ShowSnackBar(val message: String) : UiEvent()
}