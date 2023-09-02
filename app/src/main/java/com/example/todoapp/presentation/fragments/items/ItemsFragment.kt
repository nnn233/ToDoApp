package com.example.todoapp.presentation.fragments.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todoapp.*
import com.example.todoapp.application.components.TodoApplication
import com.example.todoapp.presentation.fragments.items.components.ItemsFragmentComponent
import com.example.todoapp.presentation.fragments.items.components.ItemsFragmentViewComponent
import com.example.todoapp.presentation.view_models.ItemsViewModel

class ItemsFragment : Fragment() {
    private val applicationComponent
        get() = TodoApplication.get(requireContext()).applicationComponent
    private lateinit var fragmentComponent: ItemsFragmentComponent
    private var fragmentViewComponent: ItemsFragmentViewComponent? = null

    private val viewModel: ItemsViewModel by viewModels { applicationComponent.viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent = ItemsFragmentComponent(
            fragment = this.requireActivity(),
            viewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_items, container, false)

        fragmentViewComponent = ItemsFragmentViewComponent(
            fragmentComponent,
            root = view,
            lifecycleOwner = viewLifecycleOwner,
        ).apply {
            allItemsViewController.setUpViews()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewComponent = null
    }
}