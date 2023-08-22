package com.example.todoapp.application

import com.example.todoapp.R
import com.example.todoapp.application.components.TodoApplication

enum class ItemPriority(val text: String) {
    LOW(TodoApplication.instance.applicationContext.getString(R.string.low_priority)),
    USUAL(TodoApplication.instance.applicationContext.getString(R.string.no)),
    HIGH(TodoApplication.instance.applicationContext.getString(R.string.high_priority));
    companion object {
        fun fromString(value: String) = ItemPriority.values().first { it.text == value }
    }
}


