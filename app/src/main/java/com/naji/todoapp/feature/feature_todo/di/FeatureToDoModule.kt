package com.naji.todoapp.feature.feature_todo.di

import android.app.Application
import androidx.room.Room
import com.naji.todoapp.feature.feature_todo.data.data_source.local.ToDoDatabase
import com.naji.todoapp.feature.feature_todo.data.data_source.local.dao.ToDoDao
import com.naji.todoapp.feature.feature_todo.data.repository.ToDoRepositoryImpl
import com.naji.todoapp.feature.feature_todo.domain.repository.ToDoRepository
import com.naji.todoapp.feature.feature_todo.domain.use_case.DeleteToDoUseCase
import com.naji.todoapp.feature.feature_todo.domain.use_case.GetAllToDosUseCase
import com.naji.todoapp.feature.feature_todo.domain.use_case.GetToDoByIdUseCase
import com.naji.todoapp.feature.feature_todo.domain.use_case.ToDoUseCases
import com.naji.todoapp.feature.feature_todo.domain.use_case.UpsertToDoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureToDoModule {

    @Provides
    @Singleton
    fun provideToDoDatabase(app: Application): ToDoDatabase {
        return Room.databaseBuilder(
            app,
            ToDoDatabase::class.java,
            ToDoDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideToDoDao(db: ToDoDatabase): ToDoDao {
        return db.toDoDao
    }

    @Provides
    @Singleton
    fun provideToDoRepository(toDoDao: ToDoDao): ToDoRepository {
        return ToDoRepositoryImpl(toDoDao)
    }

    @Provides
    @Singleton
    fun provideToDoUseCases(repository: ToDoRepository): ToDoUseCases {
        return ToDoUseCases(
            getAllToDosUseCase = GetAllToDosUseCase(repository),
            getToDoByIdUseCase = GetToDoByIdUseCase(repository),
            upsertToDoUseCase = UpsertToDoUseCase(repository),
            deleteToDoUseCase = DeleteToDoUseCase(repository)
        )
    }
}