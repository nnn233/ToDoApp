package com.example.todoapp.todo_item

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.todoapp.ItemPriority
import com.example.todoapp.LongToTimeConverter
import com.example.todoapp.R
import com.example.todoapp.TodoItemUIState

class TodoItemViewHolder(
    rootView: View,
    val context: Context
) {
    private val itemDescription: EditText = rootView.findViewById(R.id.description_text)
    private val itemPriority: Spinner = rootView.findViewById(R.id.spinner_priority)
    private val itemDeadlineText: TextView = rootView.findViewById(R.id.deadline_text)
    private val itemDeadlineSwitch: SwitchCompat = rootView.findViewById(R.id.deadline_switch)
    private val itemDeleteImage: ImageView = rootView.findViewById(R.id.delete_icon)
    private val itemDeleteText: TextView = rootView.findViewById(R.id.delete_text)

    fun onBind(item: TodoItemUIState) {
        itemDescription.setText(item.description, TextView.BufferType.EDITABLE)
        when (item.importance) {
            ItemPriority.HIGH -> itemPriority.setSelection(ItemPriority.HIGH.id)
            ItemPriority.LOW -> itemPriority.setSelection(ItemPriority.LOW.id)
            else -> itemPriority.setSelection(ItemPriority.USUAL.id)
        }
        setUpDeadline(item.deadline)
        itemDeleteImage.imageTintList = context.getColorStateList(R.color.red)
        itemDeleteText.setTextColor(context.getColor(R.color.red))
    }

    private fun setUpDeadline(deadline: Long) {
        if (deadline != 0L) {
            itemDeadlineSwitch.isChecked = true
            itemDeadlineText.text = LongToTimeConverter.convertLongToDate(deadline)
        }
    }
}