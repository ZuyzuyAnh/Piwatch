package com.example.piwatch.domain.repository

interface FireStoreService {
    suspend fun addNewUserPlayList(
        userId: String
    )
    suspend fun addNewPlayList(
        userId: String
    )
    suspend fun addMoviesToPlayList(
        userId: String,
        playListName: String,
        movieId: String
    )
    suspend fun getUserPlayLists(
        userId: String
    ): List<List<String>>
}