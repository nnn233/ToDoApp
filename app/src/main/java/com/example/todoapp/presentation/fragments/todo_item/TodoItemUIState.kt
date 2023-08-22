package com.example.todoapp.presentation.fragments.todo_item

import com.example.todoapp.application.ItemPriority

data class TodoItemUIState(
    val id: String,
    var description:String,
    var importance: ItemPriority,
    var deadline:Long=0,
    var isDone:Boolean,
    val creationDate: Long,
    var modificationDate:Long=0
)