package com.example.todoapp.presentation.fragments.items.components

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.todoapp.presentation.fragments.items.ItemsViewController

class ItemsFragmentViewComponent(
    fragmentComponent: ItemsFragmentComponent,
    root: View,
    lifecycleOwner: LifecycleOwner,
) {
    val allItemsViewController = ItemsViewController(
        fragmentComponent.fragment.requireActivity(),
        root,
        fragmentComponent.adapter,
        lifecycleOwner,
        fragmentComponent.viewModel,
    )
}