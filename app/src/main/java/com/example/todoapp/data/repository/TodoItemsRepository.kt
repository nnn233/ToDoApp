package com.example.todoapp.data.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.db.LocalTodoItemDataSource
import com.example.todoapp.data.mappers.toTodoItem
import com.example.todoapp.data.mappers.toTodoItemEntity
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoItemsRepository(
    private val localItemsDataSource: LocalTodoItemDataSource
) {

    private var _items = MutableLiveData<List<TodoItemUIState>>()
    val items: LiveData<List<TodoItemUIState>>
        get() = _items

    @MainThread
    suspend fun getItems() {
        val list = withContext(Dispatchers.Default) {
            returnFromDb()
        }
        _items.value = list
    }

    suspend fun getItemById(id: String) =
        withContext(Dispatchers.Default) {
            localItemsDataSource.getItemById(id)?.toTodoItem()
        }


    suspend fun addItem(item: TodoItemUIState) =
        withContext(Dispatchers.Default) {
            val itemEntity = item.toTodoItemEntity()
            localItemsDataSource.insert(itemEntity)
            _items.postValue(returnFromDb())
        }

    suspend fun updateItem(item: TodoItemUIState) =
        withContext(Dispatchers.Default) {
            val itemEntity = item.toTodoItemEntity()
            localItemsDataSource.updateItem(itemEntity)
            _items.postValue(returnFromDb())
        }


    suspend fun deleteItem(id: String) =
        withContext(Dispatchers.Default) {
            localItemsDataSource.deleteItem(id)
            _items.postValue(returnFromDb())
        }

    suspend fun changeDoneState(id: String, isDone: Boolean) {
        withContext(Dispatchers.Default) {
            val item = getItemById(id)?.copy(isDone = isDone)
            item?.let { updateItem(it) }
            _items.postValue(
                items.value.orEmpty().map {
                    if (it.id == id) it.copy(isDone = isDone)
                    else it
                }
            )
        }
    }

    private suspend fun returnFromDb(): List<TodoItemUIState> =
        withContext(Dispatchers.Default) {
            localItemsDataSource.getItems().map { it.toTodoItem() }
        }
}
