package com.example.piwatch.domain.usecase.auth_usecase

import com.example.piwatch.domain.repository.AuthRepository
import com.example.piwatch.util.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun execute(credential: AuthCredential): Flow<Resource<AuthResult>> = authRepository.googleSignIn(credential)
}