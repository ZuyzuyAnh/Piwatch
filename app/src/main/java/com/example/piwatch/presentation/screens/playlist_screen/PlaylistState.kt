package com.example.piwatch.presentation.screens.playlist_screen

import com.example.piwatch.domain.model.PlayList

data class PlaylistState(
    val playList: PlayList = PlayList(),
    val isLoading: Boolean = false,
    val toast: Int? = null,
    val errorToast: String? = null,
    val isRemoveSuccess: Boolean = false,
    val shouldShowToast: Boolean = false,
)
