package com.example.piwatch.domain.model

data class User(
    val name: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val password: String = ""
)
