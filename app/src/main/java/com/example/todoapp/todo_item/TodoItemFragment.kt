package com.example.todoapp.todo_item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todoapp.BundleUtils.ITEM_ID
import com.example.todoapp.R
import com.example.todoapp.application.TodoApplication

class TodoItemFragment : Fragment() {
    private val applicationComponent
        get() = TodoApplication.get(requireContext()).applicationComponent
    private lateinit var fragmentComponent: TodoItemFragmentComponent
    private var fragmentViewComponent: TodoItemFragmentViewComponent? = null

    private val viewModel: TodoItemViewModel by viewModels { applicationComponent.viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val id: String? = it.getString(ITEM_ID)
            if (id != null) {
                viewModel.getItemById(id)
                Log.d("ViewModel", "GetItemById is invoked ${viewModel.item}")
            }
        }
        fragmentComponent = TodoItemFragmentComponent(
            fragment = this,
            viewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo_item, container, false)

        fragmentViewComponent = TodoItemFragmentViewComponent(
            fragmentComponent,
            root = view,
            lifecycleOwner = viewLifecycleOwner
        ).apply {
            todoItemViewController.setUpViews()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewComponent = null
    }
}