package com.example.todoapp.application.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.data.repository.TodoItemsRepository

class RefreshItemsWorker(
    private val todoItemRepository: TodoItemsRepository,
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = try {
        todoItemRepository.refreshItems()
        Result.success()
    } catch (error: Throwable) {
        Result.failure()
    }
}