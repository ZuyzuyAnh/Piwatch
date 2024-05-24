package com.example.piwatch.domain.repository

import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.model.PlayList
import com.example.piwatch.domain.model.UserPlaylist
import com.example.piwatch.util.Resource
import kotlinx.coroutines.flow.Flow

interface FireStoreService {
    suspend fun addNewUserPlayList(
        userId: String,
        sessionId: String
    )
    suspend fun addNewPlayList(
        userId: String,
        playListName: String
    ): Flow<Resource<Int>>
    suspend fun addMoviesToPlayList(
        userId: String,
        playListId: Int,
        movie: Movie,
    ): Flow<Resource<Int>>
    suspend fun getUserPlayLists(
        userId: String
    ): Flow<Resource<UserPlaylist>>
    suspend fun removeMovieFromPlaylist(
        movie: Movie,
        userId: String,
        playListId: Int,
    ): Flow<Resource<Int>>
    suspend fun deletePlaylist(
        userId: String,
        playListId: Int
    ): Flow<Resource<Int>>
    suspend fun getUserHistory(
        userId: String
    ): Flow<Resource<List<Movie>>>
    suspend fun addMoviesToHistory(
        userId: String,
        movie: Movie
    )
    suspend fun getPlaylist(
        userId: String,
        playlistId: Int
    ): Flow<Resource<PlayList>>
    suspend fun getSessionId(
        userId: String
    ): Flow<Resource<String>>
}