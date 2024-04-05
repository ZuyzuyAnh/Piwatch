package com.example.piwatch.data.local.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.piwatch.domain.model.Movie
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val isPopular: Int = 0,
    val isTopRated: Int = 0,
    val isUpcoming: Int = 0,
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