package com.example.todoapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.application.ItemPriority

@Entity(tableName = "items_table")
data class TodoItemEntity(
    @PrimaryKey
    val id: String,
    var description: String,
    var importance: ItemPriority,
    var deadline: Long = 0,
    var isDone: Boolean,
    val creation_date: Long,
    var modification_date: Long = 0
)