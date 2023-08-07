package com.example.todoapp.items

import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.BundleUtils.ITEM_ID
import com.example.todoapp.R
import com.example.todoapp.TodoItemUIState
import com.example.todoapp.recycler_view_items.ItemsAdapter
import com.example.todoapp.todo_item.TodoItemFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
    private val addItemButton: FloatingActionButton = rootView.findViewById(R.id.add_new_item)

    private var itemsCount = 0

    fun setUpViews() {
        setUpItemsList()
        setUpItemsCountView()
        setUpErrors()
        setUpCollapsingToolBar()
        setUpAdapterClickListener()
        setUpButtonClickListener()
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

    private fun setUpItemsCountView() {
        viewModel.itemsCount.observe(lifecycleOwner) { count ->
            itemsCount = count
            itemCountView.text =
                activity.applicationContext.getString(R.string.completed, itemsCount)
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
        viewModel.items.observe(lifecycleOwner) { newItems ->
            adapter.items = newItems as MutableList<TodoItemUIState>
        }
    }

    private fun setUpErrors() {
        viewModel.eventNetworkError.observe(lifecycleOwner) { networkError ->
            if (networkError && viewModel.isNetworkErrorShown.value != true) {
                Toast.makeText(
                    activity.applicationContext,
                    activity.applicationContext.getString(R.string.network_items_error),
                    LENGTH_SHORT
                ).show()
            }
        }

        viewModel.eventDbError.observe(lifecycleOwner) { dbError ->
            if (dbError && viewModel.isDbErrorShown.value != true) {
                Toast.makeText(
                    activity.applicationContext,
                    activity.applicationContext.getString(R.string.db_items_error),
                    LENGTH_SHORT
                ).show()
            }
        }
    }
}