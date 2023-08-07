package com.example.todoapp .todo_item

import android.content.res.Resources.NotFoundException
import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.ItemPriority
import com.example.todoapp.TodoItemUIState
import com.example.todoapp.TodoItemsRepository
import kotlinx.coroutines.launch
import java.io.IOException

class TodoItemViewModel(
    private val todoItemRepository: TodoItemsRepository
) : ViewModel() {
    private var _item = MutableLiveData<TodoItemUIState>()
    val item: LiveData<TodoItemUIState>
        get() = _item

    private var _eventNotFoundError = MutableLiveData(false)
    val eventNotFoundError: LiveData<Boolean>
        get() = _eventNotFoundError


    private var _isNotFoundErrorShown = MutableLiveData(false)
    val isNotFoundErrorShown: LiveData<Boolean>
        get() = _isNotFoundErrorShown

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

    fun getItemById(id: String) =
        viewModelScope.launch {
            try {
                val model = todoItemRepository.getItemById(id)
                _item.value = model
                _eventNotFoundError.value = false
                _isNotFoundErrorShown.value = false //?
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false //?
                _eventDbError.value = false
                _isDbErrorShown.value = false //?
            } catch (_: NotFoundException) {
                _eventNotFoundError.value = true
            } catch (_: IOException) {
                _eventNetworkError.value = true
            } catch (_: SQLiteException) {
                _eventDbError.value = true
            }
        }


    fun addItem(item: TodoItemUIState) =
        viewModelScope.launch {
            try {
                todoItemRepository.addItem(item)
            } catch (_: SQLiteException) {
                _eventDbError.value = true
            }
        }

    fun updateItem(item: TodoItemUIState) =
        viewModelScope.launch {
            try {
                todoItemRepository.updateItem(item)
            } catch (_: SQLiteException) {
                _eventDbError.value = true
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

    fun onDescriptionChanged(newText:String){
        _item.value?.description =newText
    }

    fun onImportanceChanged(position:Int){
        _item.value?.importance = when (position) {
            0 -> ItemPriority.LOW
            2 -> ItemPriority.HIGH
            else -> ItemPriority.USUAL
        }
    }

    fun onDeadlineTextChanged(date:Long){
        _item.value?.deadline=date
    }
}