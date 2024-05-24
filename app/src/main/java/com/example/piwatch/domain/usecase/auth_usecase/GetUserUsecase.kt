package com.example.piwatch.domain.usecase.auth_usecase

import com.example.piwatch.domain.repository.AuthRepository

class GetUserUsecase(
    private val authRepository: AuthRepository,
) {
    suspend fun execute() = authRepository.getCurrentUser()
}