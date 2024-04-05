package com.example.piwatch.data.repositoryImpl

import android.util.Log
import com.example.piwatch.domain.model.UserPlaylist
import com.example.piwatch.domain.model.playList
import com.example.piwatch.domain.repository.FireStoreService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class FireStoreServiceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
): FireStoreService {
    override suspend fun addNewUserPlayList(userId: String) {
        Log.d("add user playlist executed", "")
        val data = UserPlaylist(
            userId = userId,
            playLists = listOf(
                playList(
                    playListName = "favorite",
                    movieList = emptyList()
                )
            )
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

    override suspend fun addNewPlayList(userId: String) {
        fireStore.collection("userPlaylist")
            .whereEqualTo("userId", userId)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if(task.isSuccessful) {
                    for (document in task.result){
                        val documentId = document.id
                        fireStore.collection("userPlaylist")
                            .document(documentId)
                            .update("playLists", emptyList<String>())
                    }
                }
            })
    }

    override suspend fun addMoviesToPlayList(userId: String, playListName: String, movieId: String) {
        fireStore.collection("userPlayList")
            .whereEqualTo("userId", userId)
            .whereEqualTo("playListName", playListName)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val documentId = document.id
                        val playLists = document.get("playLists") as List<HashMap<String, Any>>

                        for (playList in playLists) {
                            if (playList["playListName"] == playListName) {
                                val movieList = playList["movieList"] as ArrayList<String>
                                movieList.add(movieId)

                                fireStore.collection("userPlaylist")
                                    .document(documentId)
                                    .update("playLists", playList)
                                break
                            }
                        }
                    }
                }
            })
    }
    override suspend fun getUserPlayLists(userId: String): List<List<String>> {
        TODO("Not yet implemented")
    }


}