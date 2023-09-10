package com.example.todoapp.data.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.db.LocalTodoItemDataSource
import com.example.todoapp.data.db.RequestEntity
import com.example.todoapp.data.mappers.toTodoItem
import com.example.todoapp.data.mappers.toTodoItemDto
import com.example.todoapp.data.mappers.toTodoItemEntity
import com.example.todoapp.data.network.HardcodedTodoItemDataSource
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class TodoItemsRepository(
    private val remoteDataSource: HardcodedTodoItemDataSource,
    private val localItemsDataSource: LocalTodoItemDataSource,
    private val requestRepository: RequestRepository
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
        withContext(Dispatchers.Default) {
            refreshItems()
        }
    }

    suspend fun refreshItems() =
        withContext(Dispatchers.Default) {
            val loadedList = remoteDataSource.getItems()
            localItemsDataSource.upsertAll(loadedList.map { it.toTodoItemEntity() })
            _items.postValue(returnFromDb())
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
            try {
                remoteDataSource.addItem(itemEntity.toTodoItemDto())
            } catch (e: IOException) {
                requestRepository.addItem(
                    RequestEntity(0, "Add", item.id)
                )
            }
        }

    suspend fun updateItem(item: TodoItemUIState) =
        withContext(Dispatchers.Default) {
            val itemEntity = item.toTodoItemEntity()
            localItemsDataSource.updateItem(itemEntity)
            _items.postValue(returnFromDb())
            try {
                remoteDataSource.updateItem(itemEntity.toTodoItemDto())
            } catch (e: IOException) {
                requestRepository.addItem(
                    RequestEntity(0, "Update", item.id)
                )
            }
        }


    suspend fun deleteItem(id: String) =
        withContext(Dispatchers.Default) {
            localItemsDataSource.deleteItem(id)
            _items.postValue(returnFromDb())
            try {
                remoteDataSource.deleteItem(id)
            } catch (e: IOException) {
                requestRepository.addItem(RequestEntity(request = "Delete", itemId = id))
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
            localItemsDataSource.getItems().map { it.toTodoItem() }
        }
}
