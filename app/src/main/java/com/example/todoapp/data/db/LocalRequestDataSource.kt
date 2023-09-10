package com.example.todoapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface LocalRequestDataSource {
    @Transaction
    @Query("SELECT * FROM items_table, requests_table")
    suspend fun getAll(): List<RequestWithItem>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(request: RequestEntity)

    @Transaction
    @Query("DELETE FROM requests_table WHERE request_id=:id")
    suspend fun delete(id: Int)
}