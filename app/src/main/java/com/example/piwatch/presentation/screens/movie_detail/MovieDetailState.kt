package com.example.piwatch.presentation.screens.movie_detail

import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.model.MovieDetail
import com.example.piwatch.domain.model.PlayList

data class MovieDetailState(
    val movie: MovieDetail = MovieDetail(),

    val similarMovies: List<Movie> = emptyList(),
    val trailerKey: String? = null,

    val isLoading: Boolean = true,
    val isAddingLoading: Boolean = false,

    val playList: List<PlayList> = emptyList(),
    val isRatingSuccess: Boolean = false,
    val isAddToPlaylistSuccess: Boolean = false,
    val shouldShowToast: Boolean = false,
    val message: Int = 0
)
