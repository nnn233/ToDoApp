package com.example.todoapp.application.components

import android.app.Application
import android.content.Context
import androidx.work.WorkManager
import com.example.todoapp.data.db.TodoItemRoomDatabase

class TodoApplication : Application() {

    private val database by lazy { TodoItemRoomDatabase.getDatabase(applicationContext) }
    private val workManager by lazy{WorkManager.getInstance(applicationContext)}
    val applicationComponent by lazy { ApplicationComponent(database, workManager) }

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