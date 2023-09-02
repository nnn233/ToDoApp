package com.example.todoapp.presentation.fragments.items.recycler_view_items

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.R
import com.example.todoapp.application.ItemPriority
import com.example.todoapp.presentation.fragments.converters.LongDateToStringConverter
import com.example.todoapp.presentation.fragments.items.TodoItemInformationDialog
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState


class ItemViewHolder(
    itemView: View,
    private val activity: FragmentActivity,
    private val onChangeDoneStateListener: ((String, Boolean) -> Unit)?
) : ViewHolder(itemView) {
    private val checkBox = itemView.findViewById<CheckBox>(R.id.check_box)
    private val imagePriority = itemView.findViewById<ImageView>(R.id.image_priority)
    private var textItem = itemView.findViewById<TextView>(R.id.item_text)
    private val dateItem = itemView.findViewById<TextView>(R.id.item_date)
    private val menu = itemView.findViewById<ImageView>(R.id.extra_info)

    private lateinit var closeDialog: FrameLayout
    private lateinit var creationText: TextView
    private lateinit var modificationText: TextView
    lateinit var viewGroup:ViewGroup

    companion object {
        private const val WINDOW_WIDTH = 150
        private const val WINDOW_HEIGHT = 100
        fun create(
            parent: ViewGroup,
            activity: FragmentActivity,
            onChangeDoneStateListener: ((String, Boolean) -> Unit)?
        ): ItemViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_item, parent, false)
            return ItemViewHolder(view, activity, onChangeDoneStateListener)
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
            val dialog = TodoItemInformationDialog()
            val window = dialog.activity?.window
            window?.setLayout(WINDOW_WIDTH, WINDOW_HEIGHT)
            val halfIcon = menu.height / 2
            val xMenu = menu.x + halfIcon
            val windowManager = window?.attributes
            windowManager?.x = (xMenu - WINDOW_WIDTH / 2).toInt()
            val layout = LayoutInflater.from(activity.applicationContext).inflate(R.layout.information_dialog, null)
            closeDialog = layout.findViewById(R.id.close_window)
            creationText = layout.findViewById(R.id.creation_text)
            modificationText = layout.findViewById(R.id.modification_text)
            closeDialog.setOnClickListener {
                dialog.dismiss()
            }
            creationText.text = activity.applicationContext.getString(
                R.string.creation_date,
                LongDateToStringConverter.convertLongToDate(todoItem.creationDate)
            )
            modificationText.text = activity.applicationContext.getString(
                R.string.modification_date,
                LongDateToStringConverter.convertLongToDate(todoItem.modificationDate)
            )
            dialog.show(activity.supportFragmentManager, null)
        }
    }

    private fun setImage(priority: ItemPriority) {
        if (priority == ItemPriority.HIGH) {
            imagePriority.setImageDrawable(
                ContextCompat.getDrawable(
                    activity,
                    R.drawable.priority_high
                )
            )
        }
        if (priority == ItemPriority.LOW)
            imagePriority.setImageDrawable(
                ContextCompat.getDrawable(
                    activity,
                    R.drawable.priority_low
                )
            )
    }

    private fun applyDescriptionStyle(isDone: Boolean) {
        val textPaint: Int
        val textColor: Int
        if (isDone) {
            textPaint = textItem.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textColor = ContextCompat.getColor(activity, R.color.label_tertiary)
        } else {
            textPaint = textItem.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textColor = ContextCompat.getColor(activity, R.color.label_primary)
        }
        textItem.apply {
            paintFlags = textPaint
        }.setTextColor(textColor)
    }

}

