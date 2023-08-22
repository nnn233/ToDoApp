package com.example.todoapp.application.components

import androidx.work.WorkManager
import com.example.todoapp.application.factories.ViewModelFactory
import com.example.todoapp.data.network.HardcodedTodoItemDataSource
import com.example.todoapp.data.db.TodoItemRoomDatabase
import com.example.todoapp.data.repository.ItemsTasksRepository
import com.example.todoapp.data.repository.TodoItemsRepository

class ApplicationComponent(database: TodoItemRoomDatabase, workManager: WorkManager) {
    private val hardcodedItemsDataSource = HardcodedTodoItemDataSource()
    private val itemsRepository = TodoItemsRepository(hardcodedItemsDataSource, database.todoItemDao)
    private val itemsTasksRepository = ItemsTasksRepository(workManager)

    val viewModelFactory = ViewModelFactory(itemsRepository)

    fun onCreate(){
        itemsTasksRepository.refreshItemsPeriodically()
    }
}