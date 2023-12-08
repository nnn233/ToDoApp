package com.example.todoapp.application.components

import android.app.Application
import android.content.Context
import com.example.todoapp.data.db.TodoItemRoomDatabase

class TodoApplication : Application() {

    private val database by lazy { TodoItemRoomDatabase.getDatabase(applicationContext) }
    val applicationComponent by lazy { ApplicationComponent(database) }

    companion object {
        fun get(context: Context): TodoApplication = context.applicationContext as TodoApplication
        lateinit var instance: TodoApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        applicationComponent.onCreate()
    }
}