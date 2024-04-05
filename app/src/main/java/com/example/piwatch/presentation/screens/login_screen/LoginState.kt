package com.example.piwatch.presentation.screens.login_screen

data class LoginState(
    val email: String = "",
    val emailError: Int? = null,

    val password: String = "",
    val passwordError: Int? = null,

    val loginError: String? = null,
    val isLoginSuccess: Boolean = false
)
