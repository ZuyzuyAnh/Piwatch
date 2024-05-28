package com.example.piwatch.domain.model


data class UserPlaylist(
    val userId: String? = null,
    val playLists: List<PlayList>? = null,
    val history: List<Movie>? = emptyList(),
    val tmdbSession: String? = null,
    val ratedMovies: List<Rated>? = emptyList()
)
data class Rated(
    val movieId: Int = 0,
    val rating: Float = 0f
)
data class PlayList(
    val playListId: Int? = 0,
    val playListName: String? = null,
    val movieList: List<Movie>? = null,
    val playListImg: String? = null
)