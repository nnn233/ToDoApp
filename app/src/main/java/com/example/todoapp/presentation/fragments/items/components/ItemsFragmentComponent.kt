package com.example.todoapp.presentation.fragments.items.components

import androidx.fragment.app.FragmentActivity
import com.example.todoapp.presentation.fragments.items.recycler_view_items.ItemsAdapter
import com.example.todoapp.presentation.view_models.ItemsViewModel

class ItemsFragmentComponent(
    val fragment: FragmentActivity,
    val viewModel: ItemsViewModel
) {
    val adapter = ItemsAdapter(fragment)
}