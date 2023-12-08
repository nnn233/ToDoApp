package com.example.todoapp.presentation.fragments.todo_item.views

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.example.todoapp.R
import com.example.todoapp.application.ItemPriority
import com.example.todoapp.presentation.fragments.converters.LongDateToStringConverter
import com.example.todoapp.presentation.fragments.items.ItemsFragment
import com.example.todoapp.presentation.fragments.todo_item.dialogs.DatePickerDialog
import com.example.todoapp.presentation.fragments.todo_item.dialogs.DeleteChangesDialog
import com.example.todoapp.presentation.fragments.todo_item.dialogs.DeleteItemDialog
import com.example.todoapp.presentation.fragments.todo_item.popup_menus.PriorityPopupMenu
import com.example.todoapp.presentation.view_models.TodoItemViewModel
import com.google.android.material.appbar.AppBarLayout
import java.util.Date

class TodoItemViewController(
    private val activity: FragmentActivity,
    root: View,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: TodoItemViewModel,
    private val viewHolder: TodoItemViewHolder
) {
    private val itemSave: TextView = root.findViewById(R.id.saving_item)
    private val itemClose: ImageButton = root.findViewById(R.id.closing_item)
    private val itemDescription: EditText = root.findViewById(R.id.description_text)
    private val itemPriority: TextView = root.findViewById(R.id.priority_text)
    private val itemDeadlineText: TextView = root.findViewById(R.id.deadline_text)
    private val itemDeadlineSwitch: SwitchCompat = root.findViewById(R.id.deadline_switch)
    private val itemDeleteImage: ImageView = root.findViewById(R.id.delete_icon)
    private val itemDeleteText: TextView = root.findViewById(R.id.delete_text)

    private val topBar: AppBarLayout = root.findViewById(R.id.top_navigation_bar)
    private val nestedScrollView: NestedScrollView = root.findViewById(R.id.nested_scroll_view)

    fun setUpViews() {
        setUpItem()
        setUpDeadlineChangedListener()
        setUpPriorityChangedListener()
        setUpDeleteClickListeners()
        setUpToolBarViews()
        setUpCloseClickListener()
        setUpSaveClickListener()
    }

    private fun setUpDeadlineChangedListener() {
        itemDeadlineSwitch.setOnClickListener {
            if (itemDeadlineSwitch.isChecked) {
                invokeDatePickerDialog()
            } else {
                itemDeadlineText.text = ""
            }
        }
        itemDeadlineText.setOnClickListener {
            invokeDatePickerDialog()
        }
    }

    private fun setUpPriorityChangedListener() {
        itemPriority.setOnClickListener {
            val menu = PriorityPopupMenu.create(activity, itemPriority)
            menu.setOnMenuItemClickListener {
                itemPriority.text = it.title
                true
            }
            menu.show()
        }
    }

    private fun setUpDeleteClickListeners() {
        itemDeleteText.setOnClickListener {
            deleteItem()
        }
        itemDeleteImage.setOnClickListener {
            deleteItem()
        }
    }

    private fun setUpToolBarViews() {
        nestedScrollView.setOnScrollChangeListener { v, _, _, _, _ ->
            if (v.canScrollVertically(SCROLL_DIRECTION_UP))
                topBar.elevation =
                    activity.applicationContext.resources.getDimension(R.dimen.app_bar_elevation)
            else topBar.elevation = 0f
        }
    }

    private fun setUpCloseClickListener() {
        itemClose.setOnClickListener {
            if (viewModel.item.value != getInfo())
                DeleteChangesDialog().show(activity.supportFragmentManager, null)
        }
    }

    private fun setUpSaveClickListener() {
        itemSave.setOnClickListener {
            if (!isEmptyDescription()) {
                getInfo()?.let { item ->
                    if (checkIsItemEdited())
                        viewModel.updateItem(item)
                    else viewModel.addItem(item)
                }
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment, ItemsFragment())
                    .addToBackStack(null)
                    .commit()
            } else Toast.makeText(
                activity.applicationContext,
                R.string.require_description,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setUpItem() {
        viewModel.item.observe(lifecycleOwner) { newItem ->
            if (checkIsItemEdited())
                viewHolder.onBind(newItem)
        }
    }

    private fun isEmptyDescription(): Boolean {
        return itemDescription.text.toString() == ""
    }

    private fun invokeDatePickerDialog() {
        val datePicker = DatePickerDialog.build()
        datePicker.addOnPositiveButtonClickListener {
            itemDeadlineText.text = datePicker.selection?.let { date ->
                LongDateToStringConverter.convertLongToDate(
                    date
                )
            }
        }
        datePicker.show(activity.supportFragmentManager, null)
    }

    private fun deleteItem() =
        DeleteItemDialog(viewModel).show(activity.supportFragmentManager, null)

    private fun checkIsItemEdited(): Boolean =
        viewModel.item.value != null && viewModel.item.value?.description != ""

    private fun getInfo() =
        viewModel.item.value?.copy(
            description = itemDescription.text.toString(),
            importance = ItemPriority.fromString(itemPriority.text.toString()),
            deadline = LongDateToStringConverter.convertDateToLong(itemDeadlineText.text.toString()),
            isDone = viewModel.item.value?.isDone ?: false,
            modificationDate = Date().time
        )

    companion object {
        private const val SCROLL_DIRECTION_UP = -1
    }
}
