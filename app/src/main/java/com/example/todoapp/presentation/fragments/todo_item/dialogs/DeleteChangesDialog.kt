package com.example.todoapp.presentation.fragments.todo_item.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.todoapp.R
import com.example.todoapp.presentation.fragments.items.ItemsFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteChangesDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            builder
                .setTitle(R.string.dialog_delete_changes_title)
                .setMessage(R.string.dialog_delete_changes_message)
                .setPositiveButton(
                    R.string.yes
                ) { _, _ ->
                    it.supportFragmentManager
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