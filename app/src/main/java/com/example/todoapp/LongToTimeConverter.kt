package com.example.todoapp

import java.text.SimpleDateFormat
import java.util.*

object LongToTimeConverter {
    fun convertLongToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return format.format(date)
    }

    fun convertDateToLong(dateText: String): Long {
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = format.parse(dateText) ?: null
        return date?.time ?: 0L
    }
}