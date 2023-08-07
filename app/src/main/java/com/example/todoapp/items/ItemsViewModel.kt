package com.example.todoapp.items

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.TodoItemUIState
import com.example.todoapp.TodoItemsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class ItemsViewModel(
    private val todoItemRepository: TodoItemsRepository
) : ViewModel() {

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
        refreshJob = viewModelScope.launch() {
            try {
                val gottenItems = todoItemRepository.getItems()
                _items.value = gottenItems
                _itemsCount.value = gottenItems.size
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

    fun deleteItem(id: String) =
        viewModelScope.launch {
            try {
                todoItemRepository.deleteItem(id)
            } catch (_: SQLiteException) {
                _eventDbError.value = true
            }
        }
}