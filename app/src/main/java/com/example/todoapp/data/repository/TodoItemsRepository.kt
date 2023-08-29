package com.example.todoapp.data.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.db.LocalTodoItemDataSource
import com.example.todoapp.data.mappers.toTodoItem
import com.example.todoapp.data.mappers.toTodoItemDto
import com.example.todoapp.data.mappers.toTodoItemEntity
import com.example.todoapp.data.network.HardcodedTodoItemDataSource
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoItemsRepository(
    private val remoteDataSource: HardcodedTodoItemDataSource,
    private val localDataSource: LocalTodoItemDataSource
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
        refreshItems()
    }

    suspend fun refreshItems() =
        withContext(Dispatchers.Default) {
            val loadedList = remoteDataSource.getItems()
            localDataSource.upsertAll(loadedList.map { it.toTodoItemEntity() })
            _items.postValue(returnFromDb())
        }

    suspend fun getItemById(id: String) =
        withContext(Dispatchers.Default) {
            localDataSource.getItemById(id)?.toTodoItem()
        }


    suspend fun addItem(item: TodoItemUIState) =
        withContext(Dispatchers.Default) {
            val itemEntity = item.toTodoItemEntity()
            localDataSource.insert(itemEntity)
            _items.postValue(returnFromDb())
            remoteDataSource.addItem(itemEntity.toTodoItemDto())
        }

    suspend fun updateItem(item: TodoItemUIState) =
        withContext(Dispatchers.Default) {
            val itemEntity = item.toTodoItemEntity()
            localDataSource.upsertItem(itemEntity)
            _items.postValue(returnFromDb())
            remoteDataSource.updateItem(itemEntity.toTodoItemDto())
        }


    suspend fun deleteItem(id: String) =
        withContext(Dispatchers.Default) {
            localDataSource.deleteItem(id)
            _items.postValue(returnFromDb())
            remoteDataSource.deleteItem(id)
        }

    suspend fun changeVisibleItems(isVisible: Boolean) {
        withContext(Dispatchers.Default) {
            if (isVisible)
                refreshItems()
            else _items.postValue(_items.value?.filter { !it.isDone })
        }
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
            localDataSource.getItems().map { it.toTodoItem() }
        }
}
