package com.example.todoapp.data.repository

import android.content.res.Resources.NotFoundException
import android.database.sqlite.SQLiteException
import com.example.todoapp.data.network.HardcodedTodoItemDataSource
import com.example.todoapp.data.db.LocalTodoItemDataSource
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState
import com.example.todoapp.data.mappers.toTodoItem
import com.example.todoapp.data.mappers.toTodoItemDto
import com.example.todoapp.data.mappers.toTodoItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class TodoItemsRepository(
    private val remoteDataSource: HardcodedTodoItemDataSource,
    private val localDataSource: LocalTodoItemDataSource
) {
    suspend fun refreshItems() =
        withContext(Dispatchers.Default) {
            try {
                val loadedList = remoteDataSource.getItems()
                localDataSource.upsertAll(loadedList.map { it.toTodoItemEntity() })
            } catch (e: Exception) {
                throw Exception()
            }
        }

    suspend fun getItems(): List<TodoItemUIState> =
        withContext(Dispatchers.Default) {
            try {
                refreshItems()
                returnFromDb()
            } catch (_: IOException) {
                returnFromDb()
                throw IOException()
            } catch (_: SQLiteException) {
                throw SQLiteException()
            }
        }

    suspend fun getItemById(id: String) =
        withContext(Dispatchers.Default) {
            try {
                val item = remoteDataSource.getItemById(id)
                item?.toTodoItem()
            } catch (_: IOException) {
                val item = localDataSource.getItemById(id) ?: throw NotFoundException()
                item.toTodoItem()
                throw IOException()
            } catch (_: SQLiteException) {
                throw SQLiteException()
            }
        }


    suspend fun addItem(item: TodoItemUIState) =
        withContext(Dispatchers.Default) {
            try {
                remoteDataSource.addItem(item.toTodoItemDto())
                localDataSource.insert(item.toTodoItemEntity())
            } catch (_: IOException) {
                localDataSource.insert(item.toTodoItemEntity())
                //добавить операцию в очередь до появления интернета
            } catch (_: SQLiteException) {
                throw SQLiteException()
            }
        }

    suspend fun updateItem(item: TodoItemUIState) =
        withContext(Dispatchers.Default) {
            try {
                remoteDataSource.updateItem(item.toTodoItemDto())
                localDataSource.upsertItem(item.toTodoItemEntity())
            } catch (_: IOException) {
                localDataSource.upsertItem(item.toTodoItemEntity())
                //добавить операцию в очередь до появления интернета
            } catch (_: SQLiteException) {
                throw SQLiteException()
            }
        }


    suspend fun deleteItem(id: String) =
        withContext(Dispatchers.Default) {
            try {
                remoteDataSource.deleteItem(id)
                localDataSource.deleteItem(id)
            } catch (_: IOException) {
                localDataSource.deleteItem(id)
                //добавить операцию в очередь до появления интернета
            } catch (_: SQLiteException) {
                throw SQLiteException()
            }
        }

    private suspend fun returnFromDb(): List<TodoItemUIState> =
        withContext(Dispatchers.Default) {
            try {
                val itemsFromDb = localDataSource.getItems().map { it.toTodoItem() }
                itemsFromDb
            } catch (_: SQLiteException) {
                throw SQLiteException()
            }
        }
}
