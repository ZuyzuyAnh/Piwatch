package com.example.piwatch.domain.model

data class UserPlaylist(
    val userId: String? = null,
    val playLists: List<PlayList>? = null,
    val history: List<Movie>? = emptyList(),
    val indexCount: Int = 0,
    val tmdbSession: String? = null
)

data class PlayList(
    val playListId: Int? = 0,
    val playListName: String? = null,
    val movieList: List<Movie>? = null,
    val playListImg: String? = null
)