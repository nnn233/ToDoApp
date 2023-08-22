package com.example.todoapp.data.network

import com.example.todoapp.application.ItemPriority
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemDto(
    val id: String,
    @SerialName(value = "text")
    var description: String,
    var importance: ItemPriority,
    var deadline: Long?,
    @SerialName(value = "done")
    var isDone: Boolean,
    @SerialName(value = "created_at")
    val creation_date: Long,
    @SerialName(value = "changed_at")
    var modification_date: Long?
)
