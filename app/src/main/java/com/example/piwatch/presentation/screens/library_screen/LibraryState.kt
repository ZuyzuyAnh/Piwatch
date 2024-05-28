package com.example.piwatch.presentation.screens.library_screen

import com.example.piwatch.domain.model.Genre
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.model.PlayList

data class LibraryState(
    val genres: List<Genre> = emptyList(),
    val playList: List<PlayList> = emptyList(),
    val history: List<Movie> = emptyList(),
    val isLoading: Boolean = true,
    val isAddPlaylistLoading: Boolean = false,
    val error: Boolean = false,
    val swipRefresh: Boolean = false,
    val userName: String? = null,
    val shouldShowToast: Boolean = false,
    val isAddingSuccess: Boolean = false,
    val isRemoveSuccess: Boolean = false,
    val errorToast: String? = null,
    val toast: Int? = null
)
