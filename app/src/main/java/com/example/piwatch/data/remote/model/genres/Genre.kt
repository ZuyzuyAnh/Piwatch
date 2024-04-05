package com.example.piwatch.data.remote.model.genres


import com.example.piwatch.data.local.model.GenreEntity
import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

fun Genre.toGenreEntity() = GenreEntity(
    id = id,
    name = name
)