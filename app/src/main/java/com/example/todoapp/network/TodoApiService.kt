package com.example.todoapp.network

import com.example.todoapp.TodoItemDto
import retrofit2.http.*

interface TodoApiService {
    @GET("list")
    suspend fun getTodoItems(): List<TodoItemDto>

    @PATCH("list")
    suspend fun updateTodoItems(): List<TodoItemDto>

    @GET("list/{id}")
    suspend fun getTodoItemById(id: String): TodoItemDto

    @POST("list")
    suspend fun addTodoItem(id: String): TodoItemDto

    @PUT("list/{id}")
    suspend fun putTodoItemById(id: String): TodoItemDto

    @DELETE("list/{id}")
    suspend fun deleteTodoItemById(id: String): TodoItemDto

    // TODO: Add authorization
}