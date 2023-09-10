package com.example.todoapp.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class RequestWithItem(
    @Embedded
    val item: TodoItemEntity,
    @Relation(
        entity = RequestEntity::class,
        parentColumn = "id",
        entityColumn = "item_id"
    )
    val request: RequestEntity
)