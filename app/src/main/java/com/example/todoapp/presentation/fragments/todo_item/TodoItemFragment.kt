package com.example.todoapp.presentation.fragments.todo_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todoapp.presentation.fragments.utils.BundleUtils.ITEM_ID
import com.example.todoapp.R
import com.example.todoapp.application.components.TodoApplication
import com.example.todoapp.presentation.fragments.todo_item.components.TodoItemFragmentComponent
import com.example.todoapp.presentation.fragments.todo_item.components.TodoItemFragmentViewComponent
import com.example.todoapp.presentation.view_models.TodoItemViewModel

class TodoItemFragment : Fragment() {
    private val applicationComponent
        get() = TodoApplication.get(requireContext()).applicationComponent
    private lateinit var fragmentComponent: TodoItemFragmentComponent
    private var fragmentViewComponent: TodoItemFragmentViewComponent? = null

    private val viewModel: TodoItemViewModel by viewModels { applicationComponent.viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(ITEM_ID)?.let {
            viewModel.getItemById(it)
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