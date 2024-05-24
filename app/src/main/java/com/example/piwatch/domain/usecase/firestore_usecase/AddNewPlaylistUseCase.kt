package com.example.piwatch.domain.usecase.firestore_usecase

import com.example.piwatch.domain.repository.FireStoreService
import javax.inject.Inject

class AddNewPlaylistUseCase @Inject constructor(
    private val fireStoreService: FireStoreService
) {
    suspend fun execute(userId: String, playlistName: String) = fireStoreService.addNewPlayList(userId, playlistName)
}