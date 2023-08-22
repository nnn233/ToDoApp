package com.example.todoapp.presentation.fragments.items.components

import androidx.fragment.app.Fragment
import com.example.todoapp.application.components.ApplicationComponent
import com.example.todoapp.presentation.view_models.ItemsViewModel
import com.example.todoapp.presentation.fragments.items.recycler_view_items.ItemsAdapter

class ItemsFragmentComponent(
    val applicationComponent: ApplicationComponent,
    val fragment: Fragment,
    val viewModel: ItemsViewModel
) {
    val adapter = ItemsAdapter()
}