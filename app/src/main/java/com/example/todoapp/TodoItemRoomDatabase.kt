package com.example.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItemEntity::class], version = 1, exportSchema = false)
abstract class TodoItemRoomDatabase : RoomDatabase() {

    abstract val todoItemDao: LocalDataSource

    companion object {
        @Volatile
        private var INSTANCE: TodoItemRoomDatabase? = null

        fun getDatabase(context: Context): TodoItemRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoItemRoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}