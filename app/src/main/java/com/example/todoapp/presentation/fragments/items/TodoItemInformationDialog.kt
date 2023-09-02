package com.example.todoapp.presentation.fragments.items

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.todoapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TodoItemInformationDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            builder
                .setView(R.layout.information_dialog)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}