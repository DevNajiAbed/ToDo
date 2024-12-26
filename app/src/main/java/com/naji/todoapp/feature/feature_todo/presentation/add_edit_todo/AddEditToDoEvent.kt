package com.naji.todoapp.feature.feature_todo.presentation.add_edit_todo

sealed class AddEditToDoEvent {
    data class ChangeColor(val color: Int) : AddEditToDoEvent()
    data class ChangeTitle(val title: String) : AddEditToDoEvent()
    data class ChangeDescription(val description: String) : AddEditToDoEvent()
    data object SaveToDo : AddEditToDoEvent()
}