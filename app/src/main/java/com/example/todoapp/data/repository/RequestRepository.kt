package com.example.todoapp.data.repository

import com.example.todoapp.data.db.LocalRequestDataSource
import com.example.todoapp.data.db.RequestEntity
import com.example.todoapp.data.db.RequestWithItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestRepository(
    private val localRequestDataSource: LocalRequestDataSource
) {
    suspend fun getItems(): List<RequestWithItem> =
        withContext(Dispatchers.Default) {
            localRequestDataSource.getAll()
        }

    suspend fun addItem(item: RequestEntity) =
        withContext(Dispatchers.Default) {
            localRequestDataSource.insert(item)
        }

    suspend fun deleteItem(id: Int) =
        withContext(Dispatchers.Default) {
            localRequestDataSource.delete(id)
        }
}
