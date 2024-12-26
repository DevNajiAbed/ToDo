package com.naji.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.naji.todoapp.core.Screen
import com.naji.todoapp.feature.feature_todo.domain.model.ToDo
import com.naji.todoapp.feature.feature_todo.presentation.add_edit_todo.AddEditToDoScreen
import com.naji.todoapp.feature.feature_todo.presentation.todo_list.ToDoListScreen
import com.naji.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ToDoListScreen.route
                    ) {
                        composable(route = Screen.ToDoListScreen.route) {
                            ToDoListScreen(navController)
                        }
                        composable(
                            route = Screen.AddEditToDoScreen.route +
                                    "?${ToDo.NAV_PARAM_ID}={${ToDo.NAV_PARAM_ID}}" +
                                    "&${ToDo.NAV_PARAM_COLOR}={${ToDo.NAV_PARAM_COLOR}}",
                            arguments = listOf(
                                navArgument(
                                    name = "todoId",
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "todoColor",
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt(ToDo.NAV_PARAM_COLOR) ?: -1
                            AddEditToDoScreen(
                                navController = navController,
                                color = color
                            )
                        }
                    }
                }
            }
        }
    }
}