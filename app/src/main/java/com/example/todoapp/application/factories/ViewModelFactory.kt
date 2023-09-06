package com.example.todoapp.application.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.domain.DeleteItemUseCase
import com.example.todoapp.presentation.view_models.ItemsViewModel
import com.example.todoapp.presentation.view_models.TodoItemViewModel

class ViewModelFactory(
    private val itemsRepository: TodoItemsRepository,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ItemsViewModel::class.java -> ItemsViewModel(
            itemsRepository,
            deleteItemUseCase
        )

        TodoItemViewModel::class.java -> TodoItemViewModel(
            itemsRepository,
            deleteItemUseCase
        )

        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}