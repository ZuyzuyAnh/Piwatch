package com.example.piwatch.domain.model


import com.example.piwatch.data.local.model.MovieEntity

data class Movie(
    val id: Long = 0,
    val posterPath: String? = null,
    val title: String? = null,
    val voteAverage: Double = 0.0,
    val isPopular: Boolean = false,
    val isTopRated: Boolean = false,
    val isUpcoming: Boolean = false,
) {
}
fun Movie.toMovieEntity() = MovieEntity(
    id = id!!,
    posterPath = posterPath!!,
    title = title!!,
    voteAverage = voteAverage,
    isPopular = isPopular,
    isTopRated = isTopRated,
    isUpcoming = isUpcoming,
)