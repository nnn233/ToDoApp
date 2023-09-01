package com.example.todoapp.presentation.view_models

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.presentation.fragments.items.ItemsUIState
import kotlinx.coroutines.launch
import java.io.IOException

class ItemsViewModel(
    private val todoItemRepository: TodoItemsRepository
) : ViewModel() {

    private var _itemsState = MediatorLiveData(ItemsUIState(items = listOf()))
    val itemsState: LiveData<ItemsUIState>
        get() = _itemsState

    private var _errorState = MutableLiveData<ErrorState>()
    val errorState: LiveData<ErrorState>
        get() = _errorState

    init {
        _itemsState.addSource(todoItemRepository.items) { list ->
            if(_itemsState.value?.isDoneItemsVisible != false)
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
            try {
                todoItemRepository.getItems()
                _errorState.postValue(ErrorState())
            } catch (_: IOException) {
                _errorState.postValue(ErrorState(remoteError = true))
            } catch (_: SQLiteException) {
                ErrorState(dbError = true)
            }
        }
    }

    fun onChangedVisibilityState() {
        viewModelScope.launch {
            try {
                val isVisible = !(_itemsState.value?.isDoneItemsVisible ?: true)
                _itemsState.postValue(itemsState.value?.copy(
                    isDoneItemsVisible = isVisible
                ))
                refreshDataItems()
            } catch (_: IOException) {
                _errorState.postValue(ErrorState(remoteError = true))
            } catch (_: SQLiteException) {
                ErrorState(dbError = true)
            }
        }
    }

    fun onChangedDoneState(id: String, isDone: Boolean) {
        viewModelScope.launch {
            try {
                todoItemRepository.changeDoneState(id, isDone)
            } catch (_: IOException) {
                _errorState.postValue(ErrorState(remoteError = true))
            } catch (_: SQLiteException) {
                ErrorState(dbError = true)
            }
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch {
            try {
                todoItemRepository.deleteItem(id)
            } catch (_: IOException) {
                _errorState.postValue(ErrorState(remoteError = true))
            } catch (_: SQLiteException) {
                ErrorState(dbError = true)
            }
        }
    }
}
