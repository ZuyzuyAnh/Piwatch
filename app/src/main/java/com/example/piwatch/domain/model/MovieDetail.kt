package com.example.piwatch.domain.model

import com.example.piwatch.data.remote.model.movieDetail.Genre

data class MovieDetail(
    val backdropPath: String = "",
    val genres: List<Genre> = emptyList(),
    val id: Int = 0,
    val overview: String = "",
    val posterPath: String = "",
    val releaseDate: String = "",
    val status: String = "",
    val title: String = "",
    val voteAverage: Double = 0.0,
    val imdbId: String = "",
    var trailerKey: String? = null,
)
