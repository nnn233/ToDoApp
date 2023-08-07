package com.example.todoapp.items

import android.view.View
import androidx.lifecycle.LifecycleOwner

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