package com.example.todoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.items.ItemsViewModel
import com.example.todoapp.todo_item.TodoItemViewModel

class ViewModelFactory(
    private val itemsRepository: TodoItemsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ItemsViewModel::class.java -> ItemsViewModel(
            itemsRepository
        )
        TodoItemViewModel::class.java->TodoItemViewModel(
            itemsRepository
        )
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}