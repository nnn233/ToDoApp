package com.example.todoapp.domain

import com.example.todoapp.data.repository.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteItemUseCase(
    private val todoItemRepository: TodoItemsRepository
) {
    suspend operator fun invoke(id: String) =
        withContext(Dispatchers.Default) {
            todoItemRepository.deleteItem(id)
        }
}