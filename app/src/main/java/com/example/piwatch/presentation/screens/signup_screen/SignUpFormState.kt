package com.example.piwatch.presentation.screens.signup_screen

data class SignUpFormState(
    val userName: String = "",
    val userNameError: Int? = null,

    val email: String = "",
    val emailError: Int? = null,

    val password: String = "",
    val passwordError: Int? = null,

    val passwordConfrim: String = "",
    val passwordConfrimError: Int? = null,

    val isSignUpSucess: Boolean = false,
    val isLoading: Boolean = false,

    val toast: Int? = null,
    val errorToast: String? = null,
)
