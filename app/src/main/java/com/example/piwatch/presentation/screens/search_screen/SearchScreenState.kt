package com.example.piwatch.presentation.screens.search_screen

import com.example.piwatch.domain.model.Movie

data class SearchScreenState(
    val movieList: List<Movie> = emptyList(),
    val isSearching: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
