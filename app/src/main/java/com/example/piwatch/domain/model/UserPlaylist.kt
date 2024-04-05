package com.example.piwatch.domain.model

data class UserPlaylist(
    val userId: String? = null,
    val playLists: List<playList>? = null
)

data class playList(
    val playListName: String? = null,
    val movieList: List<String>? = null,
)