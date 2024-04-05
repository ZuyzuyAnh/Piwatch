package com.example.piwatch.presentation.screens.signup_screen

sealed class SignupFormEvent {
    data class UsernameChanged(val userName: String): SignupFormEvent()
    data class EmailChanged(val email: String): SignupFormEvent()
    data class PasswordChanged(val password: String): SignupFormEvent()
    data class PasswordConfirmChanged(val password: String, val passwordConfirm: String): SignupFormEvent()

    object Submit: SignupFormEvent()
}