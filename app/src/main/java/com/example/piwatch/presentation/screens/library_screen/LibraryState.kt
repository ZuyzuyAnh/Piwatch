package com.example.piwatch.presentation.screens.library_screen

import com.example.piwatch.domain.model.Genre
import com.example.piwatch.domain.model.Movie

data class LibraryState(
    val genres: List<Genre> = emptyList(),
    val playList: List<List<Movie>> = emptyList(),
    val history: List<Movie> = emptyList()
)
