package com.example.todoapp.presentation.fragments.items.recycler_view_items

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState


class ItemsAdapter : RecyclerView.Adapter<ViewHolder>(), ItemTouchHelperAdapter {
    private var onClickListener: OnClickListener? = null

    var onChangeDoneStateListener: ((String, Boolean) -> Unit)? = null
    var onDeleteItemListener: ((String) -> Unit)? = null
    var items = mutableListOf<TodoItemUIState>()
        set(value) {
            val callback = CommonCallback(
                oldItems = field,
                newItems = value,
                { oldItem: TodoItemUIState, newItem -> oldItem.id == newItem.id })
            field = value
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemViewHolder.create(parent, onChangeDoneStateListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder as ItemViewHolder
        holder.onBind(items[position])

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener?.onClick(position, items[position])
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onItemDelete(position: Int) {
        onDeleteItemListener?.invoke(items[position].id)
    }

    override fun onItemComplete(position: Int) {
        onChangeDoneStateListener?.invoke(items[position].id, true)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: TodoItemUIState)
    }
}

