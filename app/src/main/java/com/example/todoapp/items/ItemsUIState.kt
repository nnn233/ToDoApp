package com.example.todoapp.items

import com.example.todoapp.TodoItemUIState

data class ItemsUIState(
    val isSignedIn: Boolean = false,
    val isDoneItemsVisible : Boolean = false,
    val items: List<TodoItemUIState> = listOf()
)
