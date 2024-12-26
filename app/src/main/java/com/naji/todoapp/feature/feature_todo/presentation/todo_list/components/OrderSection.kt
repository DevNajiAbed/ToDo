package com.naji.todoapp.feature.feature_todo.presentation.todo_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naji.todoapp.feature.feature_todo.domain.util.OrderType
import com.naji.todoapp.feature.feature_todo.domain.util.ToDoOrder

@Composable
fun OrderSection(
    modifier: Modifier,
    toDoOrder: ToDoOrder = ToDoOrder.Date(OrderType.Descending),
    onOrderChange: (ToDoOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DefaultRadioButton(
                text = "Date",
                onSelect = {
                    onOrderChange(ToDoOrder.Date(toDoOrder.orderType))
                }
            )
            DefaultRadioButton(
                text = "Title",
                onSelect = {
                    onOrderChange(ToDoOrder.Title(toDoOrder.orderType))
                }
            )
            DefaultRadioButton(
                text = "Description",
                onSelect = {
                    onOrderChange(ToDoOrder.Description(toDoOrder.orderType))
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                onSelect = {
                    onOrderChange(toDoOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                onSelect = {
                    onOrderChange(toDoOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}