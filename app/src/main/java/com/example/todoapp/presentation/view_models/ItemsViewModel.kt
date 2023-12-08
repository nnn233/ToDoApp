package com.example.todoapp.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.presentation.fragments.items.ItemsUIState
import kotlinx.coroutines.launch

class ItemsViewModel(
    private val todoItemRepository: TodoItemsRepository
) : ViewModel() {

    private var _itemsState = MediatorLiveData(ItemsUIState(items = listOf()))
    val itemsState: LiveData<ItemsUIState>
        get() = _itemsState

    init {
        _itemsState.addSource(todoItemRepository.items) { list ->
            if (_itemsState.value?.isDoneItemsVisible != false)
                _itemsState.value = itemsState.value?.copy(
                    items = list ?: listOf(),
                    itemsCount = list?.filter { it.isDone }?.size ?: 0
                )
            else _itemsState.value = itemsState.value?.copy(
                items = list?.filter { !it.isDone } ?: listOf()
            )
        }
        refreshDataItems()
    }

    private fun refreshDataItems() {
        viewModelScope.launch {
            todoItemRepository.getItems()
        }
    }

    fun onChangedVisibilityState() {
        viewModelScope.launch {
            val isVisible = !(_itemsState.value?.isDoneItemsVisible ?: true)
            _itemsState.postValue(
                itemsState.value?.copy(
                    isDoneItemsVisible = isVisible
                )
            )
            refreshDataItems()
        }
    }

    fun onChangedDoneState(id: String, isDone: Boolean) {
        viewModelScope.launch {
            todoItemRepository.changeDoneState(id, isDone)
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch {
            todoItemRepository.deleteItem(id)
        }
    }
}
