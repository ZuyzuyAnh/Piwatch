package com.example.piwatch.presentation.screens.movie_with_genre

import com.example.piwatch.domain.model.Movie

data class MovieWithGenreState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val genre: String? = null
)
