package com.example.todoapp.network.mocks

import java.io.File

object FileToStringConverter {
    public fun getFileAsString(filePath: String): String {
        val uri = ClassLoader.getSystemResource(filePath)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}
