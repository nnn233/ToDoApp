package com.example.todoapp.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState
import kotlinx.coroutines.launch

class TodoItemViewModel(
    private val todoItemRepository: TodoItemsRepository
) : ViewModel() {
    private var _item = MutableLiveData(TodoItemUIState())
    val item: LiveData<TodoItemUIState>
        get() = _item

    fun getItemById(id: String) {
        viewModelScope.launch {

            val model = todoItemRepository.getItemById(id)
            model?.let {
                _item.value = it
            }
        }
    }

    fun addItem(item: TodoItemUIState) {
        viewModelScope.launch {
            todoItemRepository.addItem(item)
        }
    }

    fun updateItem(item: TodoItemUIState) {
        viewModelScope.launch {
            todoItemRepository.updateItem(item)
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch {
            todoItemRepository.deleteItem(id)
        }
    }
}