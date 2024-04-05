package com.example.piwatch.domain.repository

import com.example.piwatch.data.repositoryImpl.AuthEvent
import com.example.piwatch.util.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun getCurrentUser(): Flow<AuthEvent>

    suspend fun loginnUser(email: String, password: String): Flow<Resource<AuthResult>>

    suspend fun signUpUser(userName: String, email: String, password: String, passwordConfirm: String): Flow<Resource<AuthResult>>

    suspend fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>

    suspend fun forgotpassword(email: String): Flow<Resource<Void>>

    suspend fun logout()
}