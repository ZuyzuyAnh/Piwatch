package com.example.piwatch.domain.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.piwatch.data.local.model.MovieEntity
import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val posterPath: String? = null,
    val title: String,
    val voteAverage: Double,
    val isPopular: Int = 0,
    val isTopRated: Int = 0,
    val isUpcoming: Int = 0,
)
fun Movie.toMovieEntity() = MovieEntity(
    id = id,
    posterPath = posterPath!!,
    title = title,
    voteAverage = voteAverage,
    isPopular = isPopular,
    isTopRated = isTopRated,
    isUpcoming = isUpcoming,
)