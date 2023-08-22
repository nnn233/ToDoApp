package com.example.todoapp.presentation.view_models

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.presentation.fragments.items.ItemsUIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class ItemsViewModel(
    private val todoItemRepository: TodoItemsRepository
) : ViewModel() {

    private var _itemsState = MutableLiveData(
        ItemsUIState(
            isSignedIn = false,
            isDoneItemsVisible = true,
            listOf()
        )
    )
    val itemsState: LiveData<ItemsUIState>
        get() = _itemsState

    private var _itemsCount = MutableLiveData(0)
    val itemsCount: LiveData<Int>
        get() = _itemsCount

    private var _items = MutableLiveData<List<TodoItemUIState>>()
    val items: LiveData<List<TodoItemUIState>>
        get() = _items

    private var _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError


    private var _isNetworkErrorShown = MutableLiveData(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private var _eventDbError = MutableLiveData(false)
    val eventDbError: LiveData<Boolean>
        get() = _eventDbError


    private var _isDbErrorShown = MutableLiveData(false)
    val isDbErrorShown: LiveData<Boolean>
        get() = _isDbErrorShown

    private var refreshJob: Job? = null

    init {
        refreshDataItems()
    }

    //@MainThread
    private fun refreshDataItems() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            try {
                val gottenItems = todoItemRepository.getItems()
                _items.value = gottenItems
                _itemsCount.value = gottenItems.filter { it.isDone }.size
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false //?
                _eventDbError.value = false
                _isDbErrorShown.value = false //?
            } catch (_: IOException) {
                _eventNetworkError.value = true
            } catch (_: SQLiteException) {
                _eventDbError.value = true
            }
        }
    }

    fun onChangedVisibilityState() {
        viewModelScope.launch {
            try {
                val isVisible = !(_itemsState.value?.isDoneItemsVisible ?: true)
                val items = todoItemRepository.getItems()
                val newItems = if (isVisible) {
                    items
                } else items.filter { !it.isDone }
                _items.value = newItems
                _itemsState.value = itemsState.value?.copy(isDoneItemsVisible = isVisible)
            } catch (_: SQLiteException) {
                _eventDbError.value = true
            }
        }
    }

    fun onChangedDoneState(id: String, isDone: Boolean) {
        viewModelScope.launch {
            try {
                val item = todoItemRepository.getItemById(id)?.copy(isDone = isDone)
                item?.let { todoItemRepository.updateItem(it) }
                val newItems = items.value.orEmpty().map {
                    if (it.id == id) it.copy(isDone = isDone)
                    else it
                }
                _items.value = newItems
                _itemsCount.value = newItems.filter { it.isDone }.size
            } catch (_: SQLiteException) {
                _eventDbError.value = true
            }
        }
    }

    fun deleteItem(id: String) =
        viewModelScope.launch {
            try {
                todoItemRepository.deleteItem(id)
            } catch (_: SQLiteException) {
                _eventDbError.value = true
            }
        }
}