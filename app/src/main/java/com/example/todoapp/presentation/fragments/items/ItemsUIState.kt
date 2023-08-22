package com.example.todoapp.presentation.fragments.items

import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState

data class ItemsUIState(
    var isSignedIn: Boolean = false,
    var isDoneItemsVisible : Boolean = true,
    var items: List<TodoItemUIState> = listOf()
)
