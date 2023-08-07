package com.example.todoapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.*

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