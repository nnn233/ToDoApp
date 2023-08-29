package com.example.todoapp.presentation.view_models

data class ErrorState(
    var remoteError: Boolean = false,
    var dbError: Boolean = false
)
