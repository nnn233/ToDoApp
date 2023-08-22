package com.example.todoapp.presentation.fragments.converters

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object LongDateToStringConverter {
    fun convertLongToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return format.format(date)
    }

    fun convertDateToLong(dateText: String): Long {
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return try {
            val date = format.parse(dateText) ?: null
            date?.time ?: 0L
        } catch (e: ParseException) {
            0L
        }
    }
}