package com.example.todoapp.presentation.fragments.todo_item.views

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.todoapp.application.ItemPriority
import com.example.todoapp.presentation.fragments.converters.LongDateToStringConverter
import com.example.todoapp.R
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState

class TodoItemViewHolder(
    rootView: View,
    val context: Context
) {
    private val itemDescription: EditText = rootView.findViewById(R.id.description_text)
    private val itemPriority: TextView = rootView.findViewById(R.id.priority_text)
    private val itemDeadlineText: TextView = rootView.findViewById(R.id.deadline_text)
    private val itemDeadlineSwitch: SwitchCompat = rootView.findViewById(R.id.deadline_switch)
    private val itemDeleteImage: ImageView = rootView.findViewById(R.id.delete_icon)
    private val itemDeleteText: TextView = rootView.findViewById(R.id.delete_text)

    fun onBind(item: TodoItemUIState) {
        itemDescription.setText(item.description, TextView.BufferType.EDITABLE)
        itemPriority.text = when (item.importance) {
            ItemPriority.HIGH -> ItemPriority.HIGH.text
            ItemPriority.LOW -> ItemPriority.LOW.text
            else -> ItemPriority.USUAL.text
        }
        setUpDeadline(item.deadline)
        itemDeleteImage.imageTintList = context.getColorStateList(R.color.red)
        itemDeleteText.setTextColor(context.getColor(R.color.red))
    }

    private fun setUpDeadline(deadline: Long) {
        if (deadline != 0L) {
            itemDeadlineSwitch.isChecked = true
            itemDeadlineText.text = LongDateToStringConverter.convertLongToDate(deadline)
        }
    }
}