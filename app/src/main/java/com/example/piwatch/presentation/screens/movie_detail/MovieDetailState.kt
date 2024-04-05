package com.example.piwatch.presentation.screens.movie_detail

import com.example.piwatch.data.remote.model.movieDetail.Genre
import com.example.piwatch.domain.model.Movie

data class MovieDetailState(
    val backdropPath: String? = null,
    val genres: List<String> = emptyList(),
    val id: Int? = null,
    val imdbId: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val status: String? = null,
    val title: String? = null,
    val voteAverage: Double? = null,
    val similarMovies: List<Movie> = emptyList(),
    val trailerKey: String? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
