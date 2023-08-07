package com.example.todoapp.recycler_view_items

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.TodoItemUIState


class ItemsAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var onClickListener: OnClickListener? = null

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
        return ItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder as ItemViewHolder
        holder.onBind(items[position])

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, items[position])
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: TodoItemUIState)
    }
}

