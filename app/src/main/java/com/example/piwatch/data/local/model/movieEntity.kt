package com.example.piwatch.data.local.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.piwatch.domain.model.Movie

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val isPopular: Boolean = false,
    val isTopRated: Boolean = false,
    val isUpcoming: Boolean = false,
)

fun MovieEntity.toMovie() = Movie(
    id = id,
    posterPath = posterPath,
    title = title,
    voteAverage = voteAverage,
    isPopular = isPopular,
    isTopRated = isTopRated,
    isUpcoming = isUpcoming,
)