package com.example.todoapp.presentation.fragments.items

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.presentation.fragments.items.recycler_view_items.ItemsAdapter
import com.example.todoapp.presentation.fragments.items.recycler_view_items.SwipeCallback
import com.example.todoapp.presentation.fragments.todo_item.TodoItemFragment
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState
import com.example.todoapp.presentation.fragments.utils.ITEM_ID
import com.example.todoapp.presentation.view_models.ItemsViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class ItemsViewController(
    private val activity: FragmentActivity,
    rootView: View,
    private val adapter: ItemsAdapter,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: ItemsViewModel,
) {
    private val recyclerView: RecyclerView = rootView.findViewById(R.id.recycler_view)
    private val collapsingToolBar: CollapsingToolbarLayout =
        rootView.findViewById(R.id.collapsing_tool_bar)
    private val itemCountView: TextView = rootView.findViewById(R.id.completed_items)
    private val itemVisibilityIcon: ImageView = rootView.findViewById(R.id.completed_items_icon)
    private val addItemButton: FloatingActionButton = rootView.findViewById(R.id.add_new_item)
    private val nullItemsText: TextView = rootView.findViewById(R.id.empty_list_message)

    fun setUpViews() {
        setUpItemsList()
        setUpItemsCountView()
        setUpErrors()
        setUpCollapsingToolBar()
        setUpAdapterClickListener()
        setUpButtonClickListener()
        setUpViewModel()
    }

    private fun setUpButtonClickListener() {
        addItemButton.setOnClickListener {
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, TodoItemFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setUpCollapsingToolBar() {
        collapsingToolBar.isTitleEnabled = true
        collapsingToolBar.title = activity.applicationContext.getString(R.string.my_items)
    }

    private fun setUpNullItemsText(toBeShown: Boolean) {
        if (toBeShown) {
            nullItemsText.visibility = View.VISIBLE
        } else {
            nullItemsText.visibility = View.GONE
        }
    }

    private fun setUpAdapterClickListener() {
        adapter.setOnClickListener(object :
            ItemsAdapter.OnClickListener {
            override fun onClick(position: Int, model: TodoItemUIState) {
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.main_fragment,
                        TodoItemFragment::class.java,
                        bundleOf(ITEM_ID to model.id)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    private fun setUpItemsList() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        val callback: ItemTouchHelper.Callback =
            SwipeCallback(activity.applicationContext, adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)

        adapter.onChangeDoneStateListener = fun(id: String, isDone: Boolean) =
            viewModel.onChangedDoneState(id, isDone)

        adapter.onDeleteItemListener = fun(id: String) {
            viewModel.deleteItem(id)
            Snackbar.make(
                recyclerView,
                R.string.item_deleted,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun setUpItemsCountView() {
        itemVisibilityIcon.setOnClickListener {
            viewModel.onChangedVisibilityState()
        }
    }

    private fun setUpViewModel() {
        viewModel.itemsState.observe(lifecycleOwner) {
            changeImage(it.isDoneItemsVisible)
            changeItemsCount(it.itemsCount)
            changeItems(it.items)
        }
    }

    private fun changeImage(isVisible: Boolean) {
        val image = if (isVisible)
            R.drawable.visibility
        else
            R.drawable.visibility_off

        itemVisibilityIcon.setImageResource(image)
    }

    private fun changeItemsCount(count: Int) {
        itemCountView.text =
            activity.applicationContext.getString(R.string.completed, count)
    }

    private fun changeItems(items: List<TodoItemUIState>) {
        if (items.isEmpty())
            setUpNullItemsText(true)
        else {
            setUpNullItemsText(false)
            adapter.items = items as MutableList<TodoItemUIState>
        }
    }


    private fun setUpErrors() {
        viewModel.errorState.observe(lifecycleOwner) { error ->
            if (error.remoteError) {
                Toast.makeText(
                    activity.applicationContext,
                    activity.applicationContext.getString(R.string.network_items_error),
                    LENGTH_SHORT
                ).show()
            }
            if (error.dbError) {
                Toast.makeText(
                    activity.applicationContext,
                    activity.applicationContext.getString(R.string.db_items_error),
                    LENGTH_SHORT
                ).show()
            }
        }
    }
}