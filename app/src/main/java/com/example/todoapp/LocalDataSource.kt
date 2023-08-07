package com.example.todoapp

import androidx.room.*

@Dao
interface LocalDataSource {
    @Query("SELECT * FROM items_table WHERE id=:id")
    suspend fun getItemById(id:String): TodoItemEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item:TodoItemEntity)

    @Upsert
    suspend fun upsertItem(item:TodoItemEntity)

    @Query("DELETE FROM items_table WHERE id=:id")
    suspend fun deleteItem(id:String)

    @Query("SELECT * FROM items_table")
    suspend fun getItems(): List<TodoItemEntity>

    @Upsert
    suspend fun upsertAll(items:List<TodoItemEntity>)

    @Query("DELETE FROM items_table")
    suspend fun clearAll()
}