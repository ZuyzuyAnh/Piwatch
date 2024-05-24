package com.example.piwatch.domain.usecase.auth_usecase

import com.example.piwatch.domain.repository.AuthRepository
import com.example.piwatch.util.Resource
import kotlinx.coroutines.flow.Flow

class PassWordResetUseCase(
    private var authRepository: AuthRepository
) {

    suspend fun execute(email: String): Flow<Resource<Void>> = authRepository.forgotpassword(email)
}