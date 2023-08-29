package com.example.todoapp.presentation.view_models

import android.database.sqlite.SQLiteException
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.presentation.fragments.items.ItemsUIState
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState
import kotlinx.coroutines.launch
import java.io.IOException

class ItemsViewModel(
    private val todoItemRepository: TodoItemsRepository
) : ViewModel() {

    private var _itemsState = MediatorLiveData(ItemsUIState(items= listOf()))

    val itemsState: LiveData<ItemsUIState>
        get() = _itemsState

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


    init {
        _itemsState.addSource(todoItemRepository.items){ list ->
            _itemsState.value=itemsState.value?.copy(
                items = list ?: listOf(),
                itemsCount = list?.filter { it.isDone }?.size ?: 0
            )
        }
        refreshDataItems()
    }


    private fun refreshDataItems() {
        viewModelScope.launch {
            try {
                todoItemRepository.getItems()
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)//?
                _eventDbError.postValue(false)
                _isDbErrorShown.postValue(false)//?
            } catch (_: IOException) {
                _eventNetworkError.postValue(true)
            } catch (_: SQLiteException) {
                _eventDbError.postValue(true)
            }
        }
    }

    fun onChangedVisibilityState() {
        viewModelScope.launch {
            try {
                val isVisible = !(_itemsState.value?.isDoneItemsVisible ?: true)
                todoItemRepository.getItems()
                /*if (!isVisible)

                _itemsState.postValue(itemsState.value?.copy(
                    items=newItems ?: listOf(),
                    itemsCount = newItems?.filter { it.isDone }?.size ?: 0,
                    isDoneItemsVisible = isVisible
                ))*/
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
                val newItems = todoItemRepository.items.value.orEmpty().map {
                    if (it.id == id) it.copy(isDone = isDone)
                    else it
                }
                _itemsState.postValue(itemsState.value?.copy(
                    itemsCount = newItems.filter { it.isDone }.size
                ))
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