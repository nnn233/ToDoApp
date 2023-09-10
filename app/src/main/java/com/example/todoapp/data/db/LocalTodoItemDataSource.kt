package com.example.todoapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface LocalTodoItemDataSource {
    @Query("SELECT * FROM items_table WHERE id=:id")
    suspend fun getItemById(id: String): TodoItemEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TodoItemEntity)

    @Update
    suspend fun updateItem(item: TodoItemEntity)

    @Query("DELETE FROM items_table WHERE id=:id")
    suspend fun deleteItem(id: String)

    @Query("SELECT * FROM items_table")
    suspend fun getItems(): List<TodoItemEntity>

    @Upsert
    suspend fun upsertAll(items: List<TodoItemEntity>)

    @Query("DELETE FROM items_table")
    suspend fun clearAll()
}