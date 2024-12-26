package com.naji.todoapp.feature.feature_todo.presentation.add_edit_todo

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import com.naji.todoapp.feature.feature_todo.presentation.add_edit_todo.components.ColorBox
import com.naji.todoapp.feature.feature_todo.presentation.add_edit_todo.components.DefaultTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditToDoScreen(
    navController: NavController,
    color: Int,
    viewModel: AddEditToDoViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                UiEvent.NavigateUp -> navController.navigateUp()
                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        hostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    val toDoBGAnimatable = remember {
        Animatable(
            Color(if(color != -1) color else state.color)
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditToDoEvent.SaveToDo)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState) }
    ) {
        ColorBoxes(state, scope, toDoBGAnimatable, viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        DefaultTextField(
            text = state.title,
            hint = "Type your title",
            onValueChange = { value ->
                viewModel.onEvent(AddEditToDoEvent.ChangeTitle(value))
            },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultTextField(
            text = state.description,
            hint = "Type the description",
            onValueChange = { value ->
                viewModel.onEvent(AddEditToDoEvent.ChangeDescription(value))
            },
            singleLine = false
        )
    }
}

@Composable
private fun ColorBoxes(
    state: AddEditToDoState,
    scope: CoroutineScope,
    toDoBGAnimatable: Animatable<Color, AnimationVector4D>,
    viewModel: AddEditToDoViewModel
) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ToDo.colors
            .map { it.toArgb() }
            .forEach { color ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(color))
                        .shadow(15.dp, CircleShape)
                        .border(
                            width = 3.dp,
                            color = if (color == state.color)
                                Color.Black
                            else
                                Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                toDoBGAnimatable.animateTo(
                                    targetValue = Color(color),
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }
                            viewModel.onEvent(AddEditToDoEvent.ChangeColor(color))
                        }
                )
            }
    }
}