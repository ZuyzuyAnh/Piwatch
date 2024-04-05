package com.example.piwatch.data.remote.model.movieDetail


import com.example.piwatch.domain.model.MovieDetail
import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
)

fun MovieDetailDto.toMovieDetail() = MovieDetail(
    backdropPath, genres, id, overview, posterPath, releaseDate, status, title, voteAverage, imdbId
)