package com.naji.todoapp.feature.feature_todo.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.naji.todoapp.feature.feature_todo.data.data_source.local.dao.ToDoDao
import com.naji.todoapp.feature.feature_todo.domain.model.ToDo

@Database(
    entities = [ToDo::class],
    version = 1
)
abstract class ToDoDatabase : RoomDatabase() {

    abstract val toDoDao: ToDoDao

    companion object {
        const val DB_NAME = "todo_db"
    }
}