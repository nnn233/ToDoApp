package com.example.todoapp.application.components

import androidx.appcompat.app.AppCompatDelegate
import androidx.work.WorkManager
import com.example.todoapp.application.factories.ViewModelFactory
import com.example.todoapp.data.network.HardcodedTodoItemDataSource
import com.example.todoapp.data.db.TodoItemRoomDatabase
import com.example.todoapp.data.repository.ItemsTasksRepository
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.domain.DeleteItemUseCase

class ApplicationComponent(database: TodoItemRoomDatabase, workManager: WorkManager) {
    private val hardcodedItemsDataSource = HardcodedTodoItemDataSource()
    private val itemsRepository =
        TodoItemsRepository(hardcodedItemsDataSource, database.todoItemDao)
    private val itemsTasksRepository = ItemsTasksRepository(workManager)
    private val deleteItemUseCase = DeleteItemUseCase(itemsRepository)

    val viewModelFactory = ViewModelFactory(itemsRepository, deleteItemUseCase)

    fun onCreate() {
        itemsTasksRepository.refreshItemsPeriodically()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}