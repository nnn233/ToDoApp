package com.example.todoapp.todo_item

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.example.todoapp.*

class TodoItemViewController(
    private val activity: FragmentActivity,
    root: View,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: TodoItemViewModel,
    private val viewHolder: TodoItemViewHolder
) {
    private val itemSave: TextView = root.findViewById(R.id.saving_item)
    private val itemClose: ImageButton = root.findViewById(R.id.closing_item)
    private val itemDescription: EditText = root.findViewById(R.id.description_text)
    private val itemPriority: Spinner = root.findViewById(R.id.spinner_priority)
    private val itemDeadlineText: TextView = root.findViewById(R.id.deadline_text)
    private val itemDeadlineSwitch: SwitchCompat = root.findViewById(R.id.deadline_switch)
    private val itemDeleteImage: ImageView = root.findViewById(R.id.delete_icon)
    private val itemDeleteText: TextView = root.findViewById(R.id.delete_text)

    private var item: TodoItemUIState? = null

    fun setUpViews() {
        setUpItem()
        setUpErrors()
        setUpDescriptionChangedListener()
        setUpPriorityChangedListener()
        setUpDeadlineChangedListener()
        setUpDeleteClickListeners()
        setUpToolBarViews()
        setUpCloseClickListener()
        setUpSaveClickListener()
    }

    private fun setUpDescriptionChangedListener() {
        itemDescription.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.onDescriptionChanged(s as String)
                }

                override fun afterTextChanged(s: Editable) {
                }
            }
        )
    }

    private fun setUpPriorityChangedListener() {
        itemPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.onImportanceChanged(position)
            }
        }
    }

    private fun setUpDeadlineChangedListener() {
        itemDeadlineSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                TODO("Implement date picker dialog and set the choice to text view")
            } else {
                viewModel.onDeadlineTextChanged(0L)
            }
        }
        itemDeadlineText.setOnClickListener {
            TODO("Implement date picker dialog and set the choice to text view")
        }
    }

    private fun setUpDeleteClickListeners() {
        itemDeleteText.setOnClickListener {
            item?.id?.let { id -> viewModel.deleteItem(id) }
        }
        itemDeleteImage.setOnClickListener {
            item?.id?.let { id -> viewModel.deleteItem(id) }
        }
    }

    private fun setUpToolBarViews() {
        // TODO: When main content scrolls, set elevation to app bar layout
    }

    private fun setUpCloseClickListener() {
        itemClose.setOnClickListener {
            DeleteChangesDialog().show(activity.supportFragmentManager, null)
        }
    }

    private fun setUpSaveClickListener() {
        itemSave.setOnClickListener {
            item?.let { model -> viewModel.addItem(model) }
        }
    }

    private fun setUpItem() {
        viewModel.item.observe(lifecycleOwner) { newItem ->
            viewHolder.onBind(newItem)
        }
    }

    private fun setUpErrors() {
        viewModel.eventNetworkError.observe(lifecycleOwner) { networkError ->
            if (networkError && viewModel.isNetworkErrorShown.value != true) {
                Toast.makeText(
                    activity.applicationContext,
                    activity.applicationContext.getString(R.string.network_items_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.eventDbError.observe(lifecycleOwner) { dbError ->
            if (dbError && viewModel.isDbErrorShown.value != true) {
                Toast.makeText(
                    activity.applicationContext,
                    activity.applicationContext.getString(R.string.db_items_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.eventNotFoundError.observe(lifecycleOwner) { notFoundError ->
            if (notFoundError && viewModel.isNotFoundErrorShown.value != true) {
                Toast.makeText(
                    activity.applicationContext,
                    activity.applicationContext.getString(R.string.not_found_item_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
