package com.naji.todoapp.feature.feature_todo.domain.util

sealed class ToDoOrder(val orderType: OrderType) {
    class Date(orderType: OrderType) : ToDoOrder(orderType)
    class Title(orderType: OrderType) : ToDoOrder(orderType)
    class Description(orderType: OrderType) : ToDoOrder(orderType)

    fun copy(orderType: OrderType): ToDoOrder {
        return this.copy(orderType = orderType)
    }
}