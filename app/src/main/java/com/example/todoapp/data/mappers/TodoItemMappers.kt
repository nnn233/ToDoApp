package com.example.todoapp.data.mappers

import com.example.todoapp.data.db.TodoItemEntity
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState

fun TodoItemEntity.toTodoItem(): TodoItemUIState = TodoItemUIState(
    id = id,
    description = description,
    importance = importance,
    deadline = deadline,
    isDone = isDone,
    creationDate = creation_date,
    modificationDate = modification_date
)

fun TodoItemUIState.toTodoItemEntity(): TodoItemEntity = TodoItemEntity(
    id = id,
    description = description,
    importance = importance,
    deadline = deadline,
    isDone = isDone,
    creation_date = creationDate,
    modification_date = modificationDate
)