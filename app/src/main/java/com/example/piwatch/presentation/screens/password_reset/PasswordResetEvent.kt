package com.example.piwatch.presentation.screens.password_reset

sealed class PasswordResetEvent {
    data class EmailChanged(val email: String): PasswordResetEvent()
    object Submit: PasswordResetEvent()
}