package com.example.piwatch.domain.usecase.login_usecase

import com.example.piwatch.domain.repository.AuthRepository
import com.example.piwatch.util.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend fun execute(email: String, password: String): Flow<Resource<AuthResult>> = authRepository.loginnUser(email, password)
}