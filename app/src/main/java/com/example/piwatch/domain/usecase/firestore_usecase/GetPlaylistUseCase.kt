package com.example.piwatch.domain.usecase.firestore_usecase

import com.example.piwatch.domain.repository.FireStoreService
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val fireStoreService: FireStoreService,
) {
    suspend fun execute(userId: String, playlistId: Int) = fireStoreService.getPlaylist(userId, playlistId)
}