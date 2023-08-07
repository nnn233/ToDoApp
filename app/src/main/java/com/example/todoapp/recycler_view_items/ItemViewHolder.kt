package com.example.todoapp.recycler_view_items

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
import com.example.todoapp.ItemPriority
import com.example.todoapp.R
import com.example.todoapp.TodoItemUIState
import java.text.SimpleDateFormat
import java.util.*


class ItemViewHolder(itemView: View, private val context: Context) : ViewHolder(itemView) {
    private val checkBox = itemView.findViewById<CheckBox>(R.id.check_box)
    private val imagePriority = itemView.findViewById<ImageView>(R.id.image_priority)
    private var textItem = itemView.findViewById<TextView>(R.id.item_text)
    private val dateItem = itemView.findViewById<TextView>(R.id.item_date)
    private val menu = itemView.findViewById<ImageView>(R.id.extra_info)

    companion object {
        fun create(parent: ViewGroup): ItemViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_item, parent, false)
            return ItemViewHolder(view, parent.context)
        }
    }

    fun onBind(todoItem: TodoItemUIState) {
        textItem.text = todoItem.description
        if (todoItem.importance == ItemPriority.HIGH) {
            imagePriority.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.priority_high
                )
            )
        }
        if (todoItem.importance == ItemPriority.LOW)
            imagePriority.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.priority_low
                )
            )

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                textItem.apply {
                    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }.setTextColor(ContextCompat.getColor(context, R.color.label_tertiary))
        }

        if(todoItem.deadline!=0L)
            dateItem.text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(todoItem.deadline)

        menu.setOnClickListener {
            // TODO: Create information dialog window
            //при нажатии появляется информационное окно
        }
    }
}

