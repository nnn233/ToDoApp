package com.example.todoapp.presentation.fragments.todo_item.components

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.todoapp.presentation.fragments.todo_item.views.TodoItemViewController
import com.example.todoapp.presentation.fragments.todo_item.views.TodoItemViewHolder

class TodoItemFragmentViewComponent(
    fragmentComponent: TodoItemFragmentComponent,
    root: View,
    lifecycleOwner: LifecycleOwner
) {
    private val todoItemViewHolder = TodoItemViewHolder(
        root,
        fragmentComponent.fragment.requireContext()
    )

    val todoItemViewController = TodoItemViewController(
        fragmentComponent.fragment.requireActivity(),
        root,
        lifecycleOwner,
        fragmentComponent.viewModel,
        todoItemViewHolder
    )
}