package com.example.todoapp.application

import androidx.work.WorkManager
import com.example.todoapp.*

class ApplicationComponent(database: TodoItemRoomDatabase, workManager: WorkManager) {
    private val hardcodedItemsDataSource = HardcodedTodoItemDataSource()
    private val itemsRepository = TodoItemsRepository(hardcodedItemsDataSource, database.todoItemDao)
    private val itemsTasksRepository = ItemsTasksRepository(workManager)

    val viewModelFactory = ViewModelFactory(itemsRepository)

    fun onCreate(){
        itemsTasksRepository.refreshItemsPeriodically()
    }
}