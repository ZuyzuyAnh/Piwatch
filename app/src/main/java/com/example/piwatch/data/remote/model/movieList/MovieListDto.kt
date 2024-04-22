package com.example.piwatch.data.remote.model.movieList


import com.example.piwatch.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieListDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

fun MovieListDto.toMovies(
    isPopular: Boolean = false,
    isTopRated: Boolean = false,
    isUpcoming: Boolean = false
): List<Movie>{
    val result = mutableListOf<Movie>()
    this.results.map {
        result.add(
            Movie(
                id = it.id.toLong(),
                posterPath = it.posterPath,
                title = it.title,
                voteAverage = it.voteAverage,
                isPopular = isPopular,
                isTopRated = isTopRated,
                isUpcoming = isUpcoming,
            )
        )
    }
    return result.toList()
}