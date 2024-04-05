package com.example.piwatch.presentation.screens.login_screen

import com.google.firebase.auth.AuthCredential

sealed class LoginEvent {
    data class EmailChanged(val email: String): LoginEvent()
    data class PasswordChanged(val password: String): LoginEvent()
    data class LoginWithGoogle(val credential: AuthCredential): LoginEvent()
    object Submit: LoginEvent()
}