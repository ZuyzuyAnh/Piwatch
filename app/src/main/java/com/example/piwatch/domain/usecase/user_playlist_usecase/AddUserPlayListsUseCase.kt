package com.example.piwatch.domain.usecase.user_playlist_usecase

import com.example.piwatch.domain.repository.FireStoreService
import javax.inject.Inject

class AddUserPlayListsUseCase @Inject constructor(
    private val fireStoreService: FireStoreService
) {
    suspend fun execute(userId: String) = fireStoreService.addNewUserPlayList(userId)
}