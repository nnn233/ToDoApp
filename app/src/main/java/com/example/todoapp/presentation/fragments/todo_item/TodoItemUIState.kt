package com.example.todoapp.presentation.fragments.todo_item

import com.example.todoapp.application.ItemPriority
import java.util.Date
import java.util.UUID

data class TodoItemUIState(
    val id: String = UUID.randomUUID().toString(),
    var description: String = "",
    var importance: ItemPriority = ItemPriority.LOW,
    var deadline: Long = 0,
    var isDone: Boolean = false,
    val creationDate: Long = Date().time,
    var modificationDate: Long = 0
)