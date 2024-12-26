package com.naji.todoapp.core

sealed class Screen(val route: String) {
    data object ToDoListScreen : Screen("todo_list_screen")
    data object AddEditToDoScreen : Screen("add_edit_todo_screen")
}