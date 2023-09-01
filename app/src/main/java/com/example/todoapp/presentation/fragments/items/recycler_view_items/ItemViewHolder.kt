package com.example.todoapp.presentation.fragments.items.recycler_view_items

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.application.ItemPriority
import com.example.todoapp.R
import com.example.todoapp.presentation.fragments.converters.LongDateToStringConverter
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState

class ItemViewHolder(
    itemView: View,
    private val context: Context,
    private val onChangeDoneStateListener: ((String, Boolean) -> Unit)?
) : ViewHolder(itemView) {
    private val checkBox = itemView.findViewById<CheckBox>(R.id.check_box)
    private val imagePriority = itemView.findViewById<ImageView>(R.id.image_priority)
    private var textItem = itemView.findViewById<TextView>(R.id.item_text)
    private val dateItem = itemView.findViewById<TextView>(R.id.item_date)
    private val menu = itemView.findViewById<ImageView>(R.id.extra_info)

    companion object {
        fun create(
            parent: ViewGroup,
            onChangeDoneStateListener: ((String, Boolean) -> Unit)?
        ): ItemViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_item, parent, false)
            return ItemViewHolder(view, parent.context, onChangeDoneStateListener)
        }
    }

    fun onBind(todoItem: TodoItemUIState) {
        checkBox.isChecked = todoItem.isDone
        checkBox.setOnClickListener {
            onChangeDoneStateListener?.invoke(todoItem.id, !todoItem.isDone)
        }

        applyDescriptionStyle(todoItem.isDone)
        textItem.text = todoItem.description

        setImage(todoItem.importance)

        if (todoItem.deadline != 0L)
            dateItem.text = LongDateToStringConverter.convertLongToDate(todoItem.deadline)

        menu.setOnClickListener {
            // TODO: Create information dialog window
            //при нажатии появляется информационное окно
        }
    }

    private fun setImage(priority: ItemPriority){
        if (priority == ItemPriority.HIGH) {
            imagePriority.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.priority_high
                )
            )
        }
        if (priority == ItemPriority.LOW)
            imagePriority.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.priority_low
                )
            )
    }

    private fun applyDescriptionStyle(isDone:Boolean){
        val textPaint: Int
        val textColor:Int
        if(isDone){
            textPaint = textItem.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textColor = ContextCompat.getColor(context, R.color.label_tertiary)
        }
        else {
            textPaint = textItem.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textColor = ContextCompat.getColor(context, R.color.label_primary)
        }
        textItem.apply {
            paintFlags = textPaint
        }.setTextColor(textColor)
    }
}

