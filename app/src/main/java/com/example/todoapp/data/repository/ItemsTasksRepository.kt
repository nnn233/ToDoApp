package com.example.todoapp.data.repository

import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoapp.application.workers.RefreshItemsWorker
import java.util.concurrent.TimeUnit

class ItemsTasksRepository(
    private val workManager: WorkManager
) {
    fun refreshItemsPeriodically() {
        val refreshItemsRequest = PeriodicWorkRequestBuilder<RefreshItemsWorker>(
            REFRESH_RATE_HOURS, TimeUnit.HOURS
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
            .addTag(TAG_REFRESH_LATEST_ITEMS)

        workManager.enqueueUniquePeriodicWork(
            REFRESH_LATEST_ITEMS_TASK,
            ExistingPeriodicWorkPolicy.KEEP,
            refreshItemsRequest.build()
        )
        Log.d("WorkerManager", "Work Manager function executed")
    }

    fun cancelRefreshingItemsPeriodically() {
        workManager.cancelAllWorkByTag(TAG_REFRESH_LATEST_ITEMS)
    }

    companion object {
        private const val REFRESH_RATE_HOURS = 8L
        private const val REFRESH_LATEST_ITEMS_TASK = "refreshLatestItemsTask"
        private const val TAG_REFRESH_LATEST_ITEMS = "refreshLatestItemsTaskTag"
    }
}