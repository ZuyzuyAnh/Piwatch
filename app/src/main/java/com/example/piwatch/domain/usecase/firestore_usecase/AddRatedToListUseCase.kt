package com.example.piwatch.domain.usecase.firestore_usecase

import com.example.piwatch.domain.model.Rated
import com.example.piwatch.domain.repository.FireStoreService
import javax.inject.Inject

class AddRatedToListUseCase @Inject constructor(
    private val fireStoreService: FireStoreService
) {
    suspend fun execute(userId: String, rated: Rated) {
        fireStoreService.addRatedToList(userId, rated)
    }
}