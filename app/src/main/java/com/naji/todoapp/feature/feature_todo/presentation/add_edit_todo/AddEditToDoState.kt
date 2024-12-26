package com.naji.todoapp.feature.feature_todo.presentation.add_edit_todo

import androidx.compose.ui.graphics.toArgb
import com.naji.todoapp.feature.feature_todo.domain.model.ToDo

data class AddEditToDoState(
    val color: Int = ToDo.colors.random().toArgb(),
    val title: String = "",
    val description: String = ""
)