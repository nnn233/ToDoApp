package com.example.todoapp.items

import androidx.fragment.app.Fragment
import com.example.todoapp.application.ApplicationComponent
import com.example.todoapp.recycler_view_items.ItemsAdapter

class ItemsFragmentComponent(
    val applicationComponent: ApplicationComponent,
    val fragment: Fragment,
    val viewModel: ItemsViewModel
) {
    val adapter = ItemsAdapter()
}