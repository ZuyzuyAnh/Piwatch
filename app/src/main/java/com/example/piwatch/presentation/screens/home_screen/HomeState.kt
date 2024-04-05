package com.example.piwatch.presentation.screens.home_screen

import com.example.piwatch.domain.model.Movie

data class HomeState(
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val userName: String? = null,
    val error: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
) {

}
