package com.example.piwatch.domain.usecase.auth_usecase

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun execute() = firebaseAuth.signOut()
}