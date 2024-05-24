package com.example.piwatch.presentation.screens.playlist_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.repository.FireStoreService
import com.example.piwatch.domain.usecase.firestore_usecase.GetPlaylistUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.RemoveMovieFromPlaylistUseCase
import com.example.piwatch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val removeMovieFromPlaylistUseCase: RemoveMovieFromPlaylistUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _playlistState = MutableStateFlow(PlaylistState())
    val playlistState = _playlistState.asStateFlow()

    private val userId: String = savedStateHandle["user_id"]!!
    private val playlistId: Int = savedStateHandle["playlist_id"]!!

    init {
        getPlaylist()
    }

    fun getPlaylist() {
        viewModelScope.launch {
            getPlaylistUseCase.execute(
                userId, playlistId
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _playlistState.value = _playlistState.value.copy(
                            playList = result.result!!,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        if (_playlistState.value.isRemoveSuccess == false) {
                            _playlistState.value = _playlistState.value.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Error -> {
                        _playlistState.value = _playlistState.value.copy(
                            errorToast = result.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun removeMovieFromPlaylist(
        movie: Movie
    ) {
        viewModelScope.launch {
            removeMovieFromPlaylistUseCase.execute(
                movie = movie,
                userId = userId,
                playlistId = playlistId
            ).collect { result ->
                if (result is Resource.Success) {
                    _playlistState.value = _playlistState.value.copy(
                        toast = result.result,
                        isRemoveSuccess = true,
                        shouldShowToast = true
                    )
                    delay(500)
                    getPlaylist()
                    _playlistState.value = _playlistState.value.copy(
                        isRemoveSuccess = false
                    )
                } else {
                    _playlistState.value = _playlistState.value.copy(
                        errorToast = result.message,
                        isRemoveSuccess = false,
                        shouldShowToast = true
                    )
                    delay(500)
                    _playlistState.value = _playlistState.value.copy(
                        isRemoveSuccess = true
                    )
                }
            }
        }
    }

    fun turnOffToast() {
        _playlistState.value = _playlistState.value.copy(
            shouldShowToast = false
        )
    }

}