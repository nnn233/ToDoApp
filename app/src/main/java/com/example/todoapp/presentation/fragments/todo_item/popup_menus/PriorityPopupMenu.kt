package com.example.todoapp.presentation.fragments.todo_item.popup_menus

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.todoapp.R
import com.example.todoapp.application.components.TodoApplication

object PriorityPopupMenu {
    fun create(context: FragmentActivity, view: TextView): PopupMenu {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.priority_menu, popupMenu.menu)

        val itemHighPriority = SpannableString(TodoApplication.instance.getString(R.string.high_priority))
        itemHighPriority.setSpan(
            ForegroundColorSpan(
                TodoApplication.instance.resources.getColor(
                    R.color.red,
                    TodoApplication.instance.theme
                )
            ),
            0,
            itemHighPriority.length,
            0
        )
        popupMenu.menu.add(itemHighPriority)

        return popupMenu
    }
}