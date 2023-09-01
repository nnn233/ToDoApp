package com.example.todoapp.presentation.fragments.items.recycler_view_items

interface ItemTouchHelperAdapter {
    fun onItemDelete(position: Int)
    fun onItemComplete(position: Int)
}