package com.example.todoapp.presentation.fragments.todo_item.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.todoapp.R
import com.example.todoapp.presentation.fragments.items.ItemsFragment
import com.example.todoapp.presentation.view_models.TodoItemViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteItemDialog(private val viewModel: TodoItemViewModel) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = MaterialAlertDialogBuilder(activity)
            builder
                .setTitle(getString(R.string.delete_element))
                .setPositiveButton(
                    R.string.yes
                ) { _, _ ->
                    viewModel.item.value?.id?.let {
                        viewModel.deleteItem(it)
                    }
                    activity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_fragment, ItemsFragment())
                        .addToBackStack(null)
                        .commit()
                }
                .setNegativeButton(
                    R.string.no
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}