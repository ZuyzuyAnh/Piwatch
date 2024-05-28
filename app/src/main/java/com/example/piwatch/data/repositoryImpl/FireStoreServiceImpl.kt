package com.example.piwatch.data.repositoryImpl

import android.util.Log
import com.example.piwatch.R
import com.example.piwatch.data.remote.model.Rating
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.model.PlayList
import com.example.piwatch.domain.model.Rated
import com.example.piwatch.domain.model.UserPlaylist
import com.example.piwatch.domain.repository.FireStoreService
import com.example.piwatch.util.Resource
import com.example.piwatch.util.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FireStoreServiceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
): FireStoreService {
    override suspend fun addNewUserPlayList(userId: String, sessionId: String) {
        val data = UserPlaylist(
            userId = userId,
            playLists = listOf(
                PlayList(
                    playListId = 0,
                    playListName = "Favorite",
                    movieList = emptyList()
                ),
                PlayList(
                    playListId = 1,
                    playListName = "Playlist #1",
                    movieList = emptyList()
                )
            ),
            ratedMovies = listOf(
                Rated()
            ),
            tmdbSession = sessionId,
        )
        fireStore.collection("userPlaylist")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {document ->
                if (document.isEmpty){
                    fireStore.collection("userPlaylist")
                        .add(data)
                        .addOnSuccessListener { Log.d("addUserPlayList", "Success") }
                        .addOnFailureListener { e -> Log.e("addUserPlayList", "${e.message}") }
                }
            }
    }

    override suspend fun addNewPlayList(userId: String, playListName: String): Flow<Resource<Int>> {
        return flow {
            try {
                val userPlaylistRef =
                    fireStore.collection("userPlaylist").whereEqualTo("userId", userId)
                userPlaylistRef.get().addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val document = querySnapshot.documents[0]
                        val userPlaylist = document.toObject(UserPlaylist::class.java)

                        val currentIndexCount = userPlaylist?.playLists?.size ?: 0
                        val newPlaylistId = currentIndexCount + 1

                        val newPlaylist = PlayList(
                            playListId = newPlaylistId,
                            playListName = playListName,
                            movieList = emptyList(),
                            playListImg = null
                        )

                        val updatedPlaylists =
                            userPlaylist?.playLists.orEmpty().toMutableList().apply {
                                add(newPlaylist)
                            }
                        Log.d("addNewPlayList", "$updatedPlaylists")
                        document.reference.update(
                            mapOf(
                                "playLists" to updatedPlaylists,
                                "indexCount" to newPlaylistId // Cập nhật indexCount mới
                            )
                        ).addOnSuccessListener {
                            Log.d("addNewPlayList", "Playlist added successfully")
                        }.addOnFailureListener { exception ->
                            Log.e("addNewPlayList", "Error adding playlist", exception)
                        }
                    }
                }
                emit(Resource.Success(R.string.add_playlist_success))
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }
    }
    override suspend fun addMoviesToPlayList(
        userId: String,
        playListId: Int,
        movie: Movie
    ): Flow<Resource<Int>> {
        return flow {
            try {
                fireStore.collection("userPlaylist")
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val documents = task.result.documents
                            val document = documents[0]
                            val userPlayList = document.toObject<UserPlaylist>()
                            val playlists = userPlayList?.playLists as MutableList<PlayList>?
                            playlists?.let {
                                val updatedList = it.map { playlist ->
                                    if (playlist.playListId == playListId) {
                                        val updatedMovieList = playlist.movieList!!.toMutableList()
                                        updatedMovieList.add(movie)
                                        playlist.copy(
                                            movieList = updatedMovieList,
                                            playListImg = updatedMovieList[updatedMovieList.size - 1].posterPath
                                        )
                                    } else {
                                        playlist
                                    }
                                }
                                document.reference.update("playLists", updatedList)
                            }
                        }
                    }
                emit(Resource.Success(R.string.add_success))
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }
    }
    override suspend fun getUserPlayLists(userId: String): Flow<Resource<UserPlaylist>> {
        return flow {
            emit(Resource.Loading())
            var error: String? = null
            lateinit var userPlayList: UserPlaylist
            coroutineScope {
                async {
                    fireStore.collection("userPlaylist")
                        .whereEqualTo("userId", userId)
                        .get()
                        .addOnSuccessListener { task ->
                            val documents = task.documents
                            val document = documents[0]
                            userPlayList = document.toObject<UserPlaylist>()!!
                        }
                        .addOnFailureListener { error = it.message }
                }.await()
            }.await()
            if (error == null) {
                emit(Resource.Success(userPlayList))
            } else {
                emit(Resource.Error(error!!))
            }
        }
    }
    override suspend fun removeMovieFromPlaylist(
        movie: Movie,
        userId: String,
        playListId: Int
    ): Flow<Resource<Int>> {
        return flow {
            try {
                val querySnapshot = fireStore.collection("userPlaylist")
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val userPlayList = document.toObject<UserPlaylist>()
                    val playLists = userPlayList?.playLists

                    playLists?.let {
                        val updatedList = it.map { playList ->
                            if (playList.playListId == playListId) {
                                val updatedMovieList = playList.movieList!!.toMutableList()
                                updatedMovieList.removeIf { it.id == movie.id }
                                val newImg =
                                    if (updatedMovieList.size == 0) null else updatedMovieList[updatedMovieList.size - 1].posterPath
                                playList.copy(
                                    movieList = updatedMovieList, playListImg = newImg
                                )
                            } else {
                                playList
                            }
                        }
                        document.reference.update("playLists", updatedList)
                    }
                    emit(Resource.Success(R.string.remove_success))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }
    }
    override suspend fun deletePlaylist(userId: String, playListId: Int): Flow<Resource<Int>> {
        return flow {
            try {
                fireStore.collection("userPlaylist")
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val documents = task.result.documents
                            val document = documents[0]
                            val userPlayList = document.toObject<UserPlaylist>()
                            val playlists = userPlayList?.playLists as MutableList<PlayList>?

                            val updatedPlaylists = playlists?.filter { it.playListId != playListId }

                            document.reference.update("playLists", updatedPlaylists)
                                .addOnSuccessListener {
                                    Log.d(
                                        "delete playlist success",
                                        "Playlist deleted successfully"
                                    )
                                }
                                .addOnFailureListener { exception ->
                                    Log.d(
                                        "delete playlist error",
                                        "Error deleting playlist: ${exception.message}"
                                    )
                                }
                        }
                    }
                emit(Resource.Success(R.string.remove_success))
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }
    }
    override suspend fun getUserHistory(userId: String): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            var history: List<Movie>? = null
            var error: String? = null
            coroutineScope {
                async {
                    fireStore.collection("userPlaylist")
                        .whereEqualTo("userId", userId)
                        .get()
                        .addOnSuccessListener {
                            val documents = it.documents
                            val document = documents[0]
                            val userPlaylist = document.toObject<UserPlaylist>()
                            history = userPlaylist?.history as List<Movie>
                        }
                        .addOnFailureListener {
                            error = it.message
                        }
                }.await()
            }.await()
            Log.d("history movie", "$history")
            if (history == null) {
                emit(Resource.Error(error!!))
            } else {
                emit(Resource.Success(history))
            }
        }
    }
    override suspend fun addMoviesToHistory(userId: String, movie: Movie) {
        fireStore.collection("userPlaylist")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                val documents = it.documents
                val document = documents[0]
                val userPlaylist = document.toObject<UserPlaylist>()
                val history = userPlaylist?.history as MutableList<Movie>
                history.removeIf { it.id == movie.id }
                history.add(movie)
                document.reference.update("history", history)
            }
    }
    override suspend fun getPlaylist(
        userId: String,
        playlistId: Int
    ): Flow<Resource<PlayList>> {
        return flow {
            emit(Resource.Loading())
            var playlist: PlayList? = null
            var error: String? = null
            coroutineScope {
                async {
                    fireStore.collection("userPlaylist")
                        .whereEqualTo("userId", userId)
                        .get()
                        .addOnSuccessListener {
                            val documents = it.documents
                            val document = documents[0]
                            val userPlaylist = document.toObject<UserPlaylist>()
                            val playlists = userPlaylist?.playLists
                            playlists?.forEach {
                                if (it.playListId == playlistId) {
                                    playlist = it
                                }
                            }
                            Log.d("get playlist by name", "$playlists")
                        }
                        .addOnFailureListener {
                            error = it.message
                        }
                }.await()
            }.await()
            if (playlist == null) {
                emit(Resource.Error(error!!))
            } else {
                emit(Resource.Success(playlist))
            }
        }
    }
    override suspend fun getSessionId(userId: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            var sessionId: String? = null
            coroutineScope {
                async {
                    fireStore.collection("userPlaylist")
                        .whereEqualTo("userId", userId)
                        .get()
                        .addOnSuccessListener {
                            val documents = it.documents
                            val document = documents[0]
                            val userPlaylist = document.toObject<UserPlaylist>()
                            sessionId = userPlaylist?.tmdbSession
                        }
                }.await()
            }.await()
            if (sessionId == null) {
                emit(Resource.Error(""))
            } else {
                emit(Resource.Success(sessionId))
            }
        }
    }

    override suspend fun addRatedToList(userId: String, rated: Rated) {
        fireStore.collection("userPlaylist")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                val documents = it.documents
                val document = documents[0]
                val userPlaylist = document.toObject<UserPlaylist>()
                val ratedList = userPlaylist?.ratedMovies as MutableList<Rated>
                ratedList.removeIf{it.movieId == rated.movieId}
                ratedList.add(rated)
                document.reference.update("ratedMovies", ratedList)
            }
    }

    override suspend fun getRatedList(userId: String): Flow<Resource<List<Rated>>> {
        return flow {
            emit(Resource.Loading())
            var ratedMovies: List<Rated>? = null
            var error: String? = null
            coroutineScope {
                async {
                    fireStore.collection("userPlaylist")
                        .whereEqualTo("userId", userId)
                        .get()
                        .addOnSuccessListener {
                            val documents = it.documents
                            val document = documents[0]
                            val userPlaylist = document.toObject<UserPlaylist>()
                            ratedMovies = userPlaylist?.ratedMovies
                        }
                        .addOnFailureListener {
                            error = it.message
                        }
                }.await()
            }.await()
            if (ratedMovies == null) {
                emit(Resource.Error(error!!))
            } else {
                emit(Resource.Success(ratedMovies))
            }
        }
    }
}