package com.example.todoapp.presentation.view_models


import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState
import kotlinx.coroutines.launch
import java.io.IOException

class TodoItemViewModel(
    private val todoItemRepository: TodoItemsRepository
) : ViewModel() {
    private var _item = MutableLiveData<TodoItemUIState>()
    val item: LiveData<TodoItemUIState>
        get() = _item

    private var _errorState = MutableLiveData<ErrorState>()
    val errorState: LiveData<ErrorState>
        get() = _errorState

    fun getItemById(id: String) =
        viewModelScope.launch {
            try {
                val model = todoItemRepository.getItemById(id)
                model?.let {
                    _item.value = it
                }
                _errorState.postValue(ErrorState())
            } catch (_: IOException) {
                _errorState.postValue(ErrorState(remoteError = true))
            } catch (_: SQLiteException) {
                _errorState.postValue(ErrorState(dbError = true))
            }
        }


    fun addItem(item: TodoItemUIState) =
        viewModelScope.launch {
            try {
                todoItemRepository.addItem(item)
            } catch (_: IOException) {
                _errorState.postValue(ErrorState(remoteError = true))
            } catch (_: SQLiteException) {
                _errorState.postValue(ErrorState(dbError = true))
            }
        }

    fun updateItem(item: TodoItemUIState) =
        viewModelScope.launch {
            try {
                todoItemRepository.updateItem(item)
            } catch (_: IOException) {
                _errorState.postValue(ErrorState(remoteError = true))
            } catch (_: SQLiteException) {
                _errorState.postValue(ErrorState(dbError = true))
            }
        }

    fun deleteItem(id: String) =
        viewModelScope.launch {
            try {
                todoItemRepository.deleteItem(id)
            } catch (_: IOException) {
                _errorState.postValue(ErrorState(remoteError = true))
            } catch (_: SQLiteException) {
                _errorState.postValue(ErrorState(dbError = true))
            }
        }
}