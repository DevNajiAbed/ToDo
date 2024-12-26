package com.naji.todoapp.feature.feature_todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.naji.todoapp.ui.theme.BabyBlue
import com.naji.todoapp.ui.theme.LightGreen
import com.naji.todoapp.ui.theme.RedOrange
import com.naji.todoapp.ui.theme.RedPink
import com.naji.todoapp.ui.theme.Violet

@Entity
data class ToDo(
    val title: String,
    val description: String,
    val color: Int,
    val done: Boolean,
    val timestamp: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) {

    companion object {
        val colors = listOf(
            RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink
        )

        const val NAV_PARAM_ID = "todoId"
        const val NAV_PARAM_COLOR = "todoColor"
    }
}