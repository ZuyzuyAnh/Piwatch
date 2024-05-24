package com.example.piwatch.domain.usecase.firestore_usecase

import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.repository.FireStoreService
import javax.inject.Inject

class AddMovieToHistoryUseCase @Inject constructor(
    private val fireStoreService: FireStoreService
) {
    suspend fun execute(userId: String, movie: Movie) = fireStoreService.addMoviesToHistory(userId, movie)
}