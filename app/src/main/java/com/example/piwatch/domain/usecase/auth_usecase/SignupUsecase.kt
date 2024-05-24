package com.example.piwatch.domain.usecase.auth_usecase

import com.example.piwatch.domain.repository.AuthRepository
import com.example.piwatch.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignupUsecase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(
        userName: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Flow<Resource<AuthResult>> = authRepository.signUpUser(userName,email, password, passwordConfirm)

}