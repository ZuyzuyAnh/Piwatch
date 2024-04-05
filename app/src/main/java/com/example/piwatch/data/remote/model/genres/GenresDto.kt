package com.example.piwatch.data.remote.model.genres


import android.provider.MediaStore.Audio.Genres
import com.example.piwatch.data.local.model.GenreEntity
import com.google.gson.annotations.SerializedName

data class GenresDto(
    @SerializedName("genres")
    val genres: List<Genre>
)

