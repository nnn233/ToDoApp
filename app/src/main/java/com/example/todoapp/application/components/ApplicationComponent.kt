package com.example.todoapp.application.components

import androidx.appcompat.app.AppCompatDelegate
import com.example.todoapp.application.factories.ViewModelFactory
import com.example.todoapp.data.db.TodoItemRoomDatabase
import com.example.todoapp.data.repository.TodoItemsRepository

class ApplicationComponent(database: TodoItemRoomDatabase) {
    private val itemsRepository =
        TodoItemsRepository(database.todoItemDao)

    val viewModelFactory = ViewModelFactory(itemsRepository)

    fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}