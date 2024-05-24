package com.example.piwatch.domain.usecase.firestore_usecase

import com.example.piwatch.domain.repository.FireStoreService
import javax.inject.Inject

class GetUserHistoryUseCase @Inject constructor(
    private val fireStoreService: FireStoreService
) {
    suspend fun execute(userId: String) = fireStoreService.getUserHistory(userId)
}