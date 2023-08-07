package com.example.todoapp.network.mocks

import com.example.todoapp.network.TodoApiService
import com.example.todoapp.TodoItemDto
import com.example.todoapp.network.mocks.MockService.mockRepos
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.MockWebServer

class TodoApiServiceImpl : TodoApiService {
    private val server = MockWebServer()

    init {
        server.start()
    }
    override suspend fun getTodoItems(): List<TodoItemDto> {
        val response= mockRepos("api/list")
        return Json.decodeFromString(response.getBody().toString())
    }

    override suspend fun updateTodoItems(): List<TodoItemDto> {
        val response= mockRepos("api/list", "UPDATE")
        return Json.decodeFromString(response.getBody().toString())
    }

    override suspend fun getTodoItemById(id: String): TodoItemDto {
        val response= mockRepos("api/list/$id")
        return Json.decodeFromString(response.getBody().toString())
    }

    override suspend fun addTodoItem(id: String): TodoItemDto {
        val response= mockRepos("api/list/$id")
        return Json.decodeFromString(response.getBody().toString())
    }

    override suspend fun putTodoItemById(id: String): TodoItemDto {
        val response= mockRepos("api/list/$id")
        return Json.decodeFromString(response.getBody().toString())
    }

    override suspend fun deleteTodoItemById(id: String): TodoItemDto {
        val response= mockRepos("api/list/$id")
        return Json.decodeFromString(response.getBody().toString())
    }
}