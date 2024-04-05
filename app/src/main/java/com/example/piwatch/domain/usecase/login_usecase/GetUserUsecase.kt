package com.example.piwatch.domain.usecase.login_usecase

import com.example.piwatch.data.repositoryImpl.AuthEvent
import com.example.piwatch.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetUserUsecase(
    private val authRepository: AuthRepository,
) {
    suspend fun execute() = authRepository.getCurrentUser()
}