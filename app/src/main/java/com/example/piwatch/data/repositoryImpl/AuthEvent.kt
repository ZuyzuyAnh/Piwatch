package com.example.piwatch.data.repositoryImpl

import com.google.firebase.auth.FirebaseUser

sealed class AuthEvent {
    data class LogedIn(val user: FirebaseUser? = null): AuthEvent()

    object LogedOut: AuthEvent()
}