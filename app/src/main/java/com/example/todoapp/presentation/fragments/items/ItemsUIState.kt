package com.example.todoapp.presentation.fragments.items

import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState

data class ItemsUIState(
    var isDoneItemsVisible: Boolean = true,
    var items: List<TodoItemUIState>,
    var itemsCount: Int = 0
)
