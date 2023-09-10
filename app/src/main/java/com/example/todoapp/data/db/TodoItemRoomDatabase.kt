package com.example.todoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TodoItemEntity::class, RequestEntity::class],
    version = 3,
    exportSchema = false
)
abstract class TodoItemRoomDatabase : RoomDatabase() {

    abstract val todoItemDao: LocalTodoItemDataSource
    abstract val requestDao: LocalRequestDataSource

    companion object {
        @Volatile
        private var INSTANCE: TodoItemRoomDatabase? = null

        fun getDatabase(context: Context): TodoItemRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoItemRoomDatabase::class.java,
                    "items_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}