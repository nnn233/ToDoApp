package com.example.todoapp.application

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.todoapp.data.mappers.toTodoItemDto
import com.example.todoapp.data.network.HardcodedTodoItemDataSource
import com.example.todoapp.data.repository.RequestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetworkReceiver(
    private val context: Context,
    private val requestRepository: RequestRepository,
    private val remoteTodoItemDataSource: HardcodedTodoItemDataSource
) : NetworkCallback() {
    private var networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    fun enable() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        CoroutineScope(Dispatchers.Default).launch {
            val list = requestRepository.getItems()
            if (list.isNotEmpty())
                list.forEach {
                    when (it.request.request) {
                        "Add" -> it.item.let { itemEntity ->
                            remoteTodoItemDataSource.addItem(
                                itemEntity.toTodoItemDto()
                            )
                        }

                        "Update" -> it.item.let { itemEntity ->
                            remoteTodoItemDataSource.updateItem(
                                itemEntity.toTodoItemDto()
                            )
                        }

                        "Delete" -> remoteTodoItemDataSource.deleteItem(it.request.itemId)
                    }
                    requestRepository.deleteItem(it.request.requestId)
                }
        }
    }
}