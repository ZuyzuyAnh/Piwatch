package com.example.piwatch.domain.usecase.firestore_usecase

import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.repository.FireStoreService
import javax.inject.Inject

class RemoveMovieFromPlaylistUseCase @Inject constructor(
    private val fireStoreService: FireStoreService
){
    suspend fun execute(movie: Movie, userId: String, playlistId: Int) = fireStoreService.removeMovieFromPlaylist(movie, userId, playlistId)
}