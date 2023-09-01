package com.example.todoapp.presentation.fragments.items.recycler_view_items

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.R


class SwipeCallback(
    context: Context,
    private val adapter: ItemTouchHelperAdapter
) : ItemTouchHelper.Callback() {
    private var background = ColorDrawable()
    private val backgroundColorDelete = getColor(context, R.color.red)
    private val backgroundColorComplete = getColor(context, R.color.green)
    private val iconDelete = getDrawable(context, R.drawable.delete_icon)
    private val iconComplete = getDrawable(context, R.drawable.baseline_check_24)

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.END)
            adapter.onItemComplete(viewHolder.adapterPosition)
        else adapter.onItemDelete(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (dX > 0)
            drawSwipeRightDirection(c, viewHolder, dX, background)
        else if (dX < 0)
            drawSwipeLeftDirection(c, viewHolder, dX, background)

        super.onChildDraw(c, recyclerView, viewHolder, dX / 4, dY, actionState, isCurrentlyActive)
    }

    private fun drawSwipeRightDirection(
        c: Canvas,
        viewHolder: ViewHolder,
        dX: Float,
        background: ColorDrawable
    ) {
        val rect = Rect(
            viewHolder.itemView.left,
            viewHolder.itemView.top,
            viewHolder.itemView.left + (dX / 4).toInt(),
            viewHolder.itemView.bottom
        )
        c.clipRect(rect)

        background.color = backgroundColorComplete
        background.bounds = rect
        background.draw(c)

        val icon = iconComplete
        icon?.let {
            val halfIcon = icon.intrinsicHeight / 2
            val top =
                viewHolder.itemView.top + ((viewHolder.itemView.bottom - viewHolder.itemView.top) / 2 - halfIcon)
            val right = (dX / 8).toInt() + halfIcon
            val left = (dX / 8).toInt() - halfIcon
            icon.setBounds(
                left,
                top,
                right,
                top + icon.intrinsicHeight
            )

            icon.draw(c)
        }
    }

    private fun drawSwipeLeftDirection(
        c: Canvas,
        viewHolder: ViewHolder,
        dX: Float,
        background: ColorDrawable
    ) {
        val rect = Rect(
            viewHolder.itemView.right + (dX / 4).toInt(),
            viewHolder.itemView.top,
            viewHolder.itemView.right,
            viewHolder.itemView.bottom
        )
        c.clipRect(rect)

        background.color = backgroundColorDelete
        background.bounds = rect
        background.draw(c)

        val icon = iconDelete
        icon?.let {
            val halfIcon = icon.intrinsicHeight / 2
            val top =
                viewHolder.itemView.top + ((viewHolder.itemView.bottom - viewHolder.itemView.top) / 2 - halfIcon)
            val right = viewHolder.itemView.right + (dX / 8).toInt() + halfIcon
            val left = viewHolder.itemView.right + (dX / 8).toInt() - halfIcon
            icon.setBounds(
                left,
                top,
                right,
                top + icon.intrinsicHeight
            )

            icon.draw(c)
        }
    }
}