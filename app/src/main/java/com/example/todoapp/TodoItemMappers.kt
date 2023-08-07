package com.example.todoapp

fun TodoItemDto.toTodoItemEntity(): TodoItemEntity = TodoItemEntity(
    id = id,
    description = description,
    importance = importance,
    deadline = deadline ?: 0L,
    isDone = isDone,
    creation_date = creation_date,
    modification_date = modification_date ?: 0L
)

fun TodoItemDto.toTodoItem(): TodoItemUIState = TodoItemUIState(
    id = id,
    description = description,
    importance = importance,
    deadline = deadline ?: 0L,
    isDone = isDone,
    creationDate = creation_date,
    modificationDate = modification_date ?: 0L
)


fun TodoItemEntity.toTodoItem(): TodoItemUIState = TodoItemUIState(
    id = id,
    description = description,
    importance = importance,
    deadline = deadline,
    isDone = isDone,
    creationDate = creation_date,
    modificationDate = modification_date
)


fun TodoItemUIState.toTodoItemDto(): TodoItemDto = TodoItemDto(
    id = id,
    description = description,
    importance = importance,
    deadline = deadline,
    isDone = isDone,
    creation_date = creationDate,
    modification_date = modificationDate
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