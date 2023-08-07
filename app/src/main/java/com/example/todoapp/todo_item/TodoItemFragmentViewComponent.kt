package com.example.todoapp.todo_item

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.todoapp.items.ItemsViewModel

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