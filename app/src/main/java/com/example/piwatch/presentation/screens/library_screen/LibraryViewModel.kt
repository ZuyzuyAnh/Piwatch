package com.example.piwatch.presentation.screens.library_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.domain.repository.FireStoreService
import com.example.piwatch.domain.usecase.auth_usecase.GetUserUsecase
import com.example.piwatch.domain.usecase.auth_usecase.SignOutUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.AddNewPlaylistUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.DeletePlaylistUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.GetUserHistoryUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.LoadUserPlaylistUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetGenresUseCase
import com.example.piwatch.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getUserUsecase: GetUserUsecase,
    private val getGenresUseCase: GetGenresUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getUserPlaylistUseCase: LoadUserPlaylistUseCase,
    private val addNewPlaylistUseCase: AddNewPlaylistUseCase,
    private var getUserHistoryUseCase: GetUserHistoryUseCase,
    private var deletePlaylistUseCase: DeletePlaylistUseCase
): ViewModel() {
    var userId: String? = null

    private val _libraryState = MutableStateFlow(LibraryState())
    val libraryState = _libraryState.asStateFlow()

    var isDialogShown by mutableStateOf(false)
        private set

    var playListName by mutableStateOf("")
        private set

    init {
        viewModelScope.launch(IO) {
            getUserId()
            Log.d("inspect library vm", "$userId")
        }
        viewModelScope.launch(IO) {
            getGenres()
        }
        viewModelScope.launch() {
            delay(500)
            viewModelScope.launch {
                loadUserHistory()
            }
            loadUserPlayList()
            Log.d("inspect library vm", "$userId")
        }
    }

    fun signOut() {
        _libraryState.value = _libraryState.value.copy(isLoading = true)
        signOutUseCase.execute()
    }
    fun onBuyClick(){
        playListName = ""
        isDialogShown = true
    }

    fun onDismissDialog(){
        isDialogShown = false
        _libraryState.value = _libraryState.value.copy(error = false)
    }

    fun onValueChange(name: String){
        playListName = name
        if (_libraryState.value.playList.any { it.playListName == playListName }) {
            _libraryState.value = _libraryState.value.copy(error = true)
        } else {
            _libraryState.value = _libraryState.value.copy(error = false)

        }
    }
    suspend fun getGenres(){
        getGenresUseCase.execute().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _libraryState.value = _libraryState.value.copy(genres = result.result!!)
                }

                is Resource.Loading -> {
                    _libraryState.value = _libraryState.value.copy(isLoading = true)
                }

                is Resource.Error -> {

                }
            }
        }
    }

    suspend fun getUserId() {
        val user = getUserUsecase.execute()
        userId = user?.uid
        _libraryState.value = _libraryState.value.copy(userName = user?.displayName)
    }

    suspend fun loadUserPlayList() {
        viewModelScope.launch {
            getUserPlaylistUseCase.execute(userId!!).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _libraryState.value = _libraryState.value.copy(
                            playList = result.result?.playLists!!,
                            isLoading = false,
                        )
                    }

                    is Resource.Loading -> {
                        if (_libraryState.value.isAddPlaylistLoading == false) {
                            _libraryState.value = _libraryState.value.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    suspend fun addNewPlayList() {
        if (_libraryState.value.error == false) {
            viewModelScope.launch {
                addNewPlaylistUseCase.execute(userId!!, playListName).collect { result ->
                    if (result is Resource.Success) {
                        _libraryState.value = _libraryState.value.copy(
                            toast = result.result,
                            shouldShowToast = true,
                            isAddingSuccess = true,
                            error = false
                        )
                        delay(1000)
                        _libraryState.value = _libraryState.value.copy(
                            isAddPlaylistLoading = true,
                            isAddingSuccess = false
                        )
                        loadUserPlayList()
                        _libraryState.value = _libraryState.value.copy(isAddPlaylistLoading = false)

                    } else {
                        _libraryState.value = _libraryState.value.copy(
                            errorToast = result.message,
                            shouldShowToast = true,
                            isAddingSuccess = false,
                        )
                        delay(1000)
                        _libraryState.value = _libraryState.value.copy(
                            isAddingSuccess = true
                        )
                    }
                }
            }
        }
    }

    suspend fun loadUserHistory() {
        getUserHistoryUseCase.execute(userId!!).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _libraryState.value = _libraryState.value.copy(
                        history = result.result!!,
                        swipRefresh = false
                    )
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }
            }
        }
    }

    fun deletePlaylist(playlistId: Int) {
        viewModelScope.launch {
            deletePlaylistUseCase.execute(userId!!, playListId = playlistId).collect { result ->
                if (result is Resource.Success) {
                    _libraryState.value = _libraryState.value.copy(
                        toast = result.result,
                        shouldShowToast = true,
                        isRemoveSuccess = true,
                        error = false
                    )
                    delay(500)
                    _libraryState.value = _libraryState.value.copy(
                        isAddPlaylistLoading = true,
                        error = false,
                        isRemoveSuccess = false
                    )
                    loadUserPlayList()
                    _libraryState.value = _libraryState.value.copy(isAddPlaylistLoading = false)
                }else{
                    _libraryState.value = _libraryState.value.copy(
                        errorToast = result.message,
                        shouldShowToast = true,
                        isRemoveSuccess = false
                    )
                    delay(500)
                    _libraryState.value = _libraryState.value.copy(
                        isRemoveSuccess = true
                    )
                }
            }
        }
    }

    fun turnOffToast() {
        _libraryState.value = _libraryState.value.copy(
            shouldShowToast = false
        )
    }
}