package com.example.todoapp.data.network

import com.example.todoapp.application.ItemPriority
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.GregorianCalendar

class HardcodedTodoItemDataSource {
    private var items = mutableListOf(
        TodoItemDto(
            id = "1", description = "Сходить в магазин", importance = ItemPriority.LOW,
            isDone = true, creation_date = Calendar.getInstance().timeInMillis,
            modification_date = null,
            deadline = null
        ),
        TodoItemDto(
            id = "2",
            description = "Погулять с собакой и с подругой и еще с кем-нибудь очень срочно",
            importance = ItemPriority.HIGH,
            deadline = GregorianCalendar(2023, 6, 22).timeInMillis,
            isDone = false,
            creation_date = Calendar.getInstance().timeInMillis,
            modification_date = null
        ),
        TodoItemDto(
            "3",
            "Посмотреть несколько видео по программированию и трансляции ШМР, и сделать что-то, что-то, что-то и еще что-то",
            ItemPriority.USUAL,
            isDone = false,
            creation_date = Calendar.getInstance().timeInMillis,
            deadline = null,
            modification_date = null
        ),
        TodoItemDto(
            "4",
            "Сделать конспект лекций",
            ItemPriority.HIGH,
            GregorianCalendar(2023, 7, 22).timeInMillis,
            isDone = false,
            creation_date = Calendar.getInstance().timeInMillis,
            Calendar.getInstance().timeInMillis
        ),
        TodoItemDto(
            "5",
            "Выучить тысячу иностранных языков, уделяя невероятное внимнаие самым сложным тонкостям грамматики и произношения, в особенности при работе с иероглифами",
            ItemPriority.LOW,
            isDone = false,
            creation_date = Calendar.getInstance().timeInMillis,
            modification_date = Calendar.getInstance().timeInMillis,
            deadline = null
        ),
        TodoItemDto(
            "6",
            "Прочитать 100 страниц книги",
            ItemPriority.USUAL,
            deadline = Calendar.getInstance().timeInMillis,
            isDone = true,
            creation_date = Calendar.getInstance().timeInMillis,
            modification_date = null
        ),
        TodoItemDto(
            "7",
            "Приготовить завтрак",
            ItemPriority.HIGH,
            isDone = false,
            creation_date = Calendar.getInstance().timeInMillis,
            modification_date = null,
            deadline = null
        ),
        TodoItemDto(
            "8",
            "Сделать еще что-нибудь",
            ItemPriority.LOW,
            deadline = GregorianCalendar(2024, 7, 22).timeInMillis,
            isDone = false,
            creation_date = Calendar.getInstance().timeInMillis,
            modification_date = null
        ),
        TodoItemDto(
            "9",
            "Сделать что-то, что-то, что-то и еще что-то",
            ItemPriority.USUAL,
            isDone = true,
            creation_date = Calendar.getInstance().timeInMillis,
            deadline = null,
            modification_date = null
        ),
        TodoItemDto(
            "10",
            "Посмотреть несколько очень важных видео",
            ItemPriority.USUAL,
            isDone = false,
            creation_date = Calendar.getInstance().timeInMillis,
            modification_date = Calendar.getInstance().timeInMillis,
            deadline = null
        )
    )

    suspend fun getItems(): List<TodoItemDto> {
        delay(300L)
        return items
    }

    suspend fun getItemById(id: String): TodoItemDto? {
        delay(300L)
        return items.find { it.id == id }
    }

    suspend fun updateItems(updatedItems: List<TodoItemDto>) {
        delay(300L)
        items = updatedItems as MutableList<TodoItemDto>
    }

    suspend fun addItem(item: TodoItemDto) {
        delay(300L)
        items.add(item)
    }

    suspend fun updateItem(item: TodoItemDto) {
        delay(300L)
        var done = false
        items.map {
            if (it.id == item.id) {
                it.description = item.description
                it.importance = item.importance
                it.deadline = item.deadline
                it.isDone = item.isDone
                it.modification_date = item.modification_date
                done = true
            }
        }
        if (!done) throw Exception()
    }

    suspend fun deleteItem(id: String) {
        delay(300L)
        items = items.filter { it.id != id } as MutableList<TodoItemDto>
    }
}