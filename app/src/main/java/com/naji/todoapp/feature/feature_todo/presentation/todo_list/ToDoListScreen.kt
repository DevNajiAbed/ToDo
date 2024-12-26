package com.naji.todoapp.feature.feature_todo.presentation.todo_list

import android.annotation.SuppressLint
import androidx.annotation.ContentView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.naji.todoapp.core.Screen
import com.naji.todoapp.feature.feature_todo.domain.use_case.ToDoUseCases
import com.naji.todoapp.feature.feature_todo.presentation.todo_list.components.OrderSection
import com.naji.todoapp.feature.feature_todo.presentation.todo_list.components.ToDoItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ToDoListScreen(
    navController: NavController,
    viewModel: ToDoListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.ToDoListScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to do"
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState) },
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            TopBar(viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onOrderChange = { toDoOrder ->
                        viewModel.onEvent(ToDoListEvent.ChangeOrder(toDoOrder))
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.toDos) { toDo ->
                    ToDoItem(
                        toDo = toDo,
                        onToDoClick = {
                            navController.navigate(Screen.AddEditToDoScreen.route + "?todoId=${toDo.id}")
                        },
                        onCheckedChange = {
                            viewModel.onEvent(ToDoListEvent.DoneToDo(toDo))
                        },
                        onDeleteClick = {
                            viewModel.onEvent(ToDoListEvent.DeleteToDo(toDo))
                            scope.launch {
                                val result = hostState.showSnackbar(
                                    message = "ToDo has been deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Long
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(ToDoListEvent.RestoreToDo)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar(
    viewModel: ToDoListViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "ToDo App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = {
            viewModel.onEvent(ToDoListEvent.ToggleOrderSection)
        }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Show/Hide order section",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}