package com.example.todoapp.presentation.fragments.todo_item.dialogs

import com.google.android.material.datepicker.MaterialDatePicker

object DatePickerDialog {
    fun build(): MaterialDatePicker<Long> {
        val builder = MaterialDatePicker.Builder.datePicker()
        return builder
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }
}