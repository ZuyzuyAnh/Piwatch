package com.example.piwatch.domain.model

import com.example.piwatch.data.remote.model.movieDetail.Genre
import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val backdropPath: String,
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val status: String,
    val title: String,
    val voteAverage: Double,
    val imdbId: String,
    var trailerKey: String? = null,
)
