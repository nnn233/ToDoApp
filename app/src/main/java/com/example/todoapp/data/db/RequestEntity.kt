package com.example.todoapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "requests_table", foreignKeys = [ForeignKey(
        entity = TodoItemEntity::class,
        parentColumns = ["id"],
        childColumns = ["item_id"],
        onDelete = CASCADE
    )]
)
data class RequestEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "request_id")
    val requestId: Int = 0,
    var request: String,
    @ColumnInfo(name = "item_id", index = true)
    var itemId: String
)
