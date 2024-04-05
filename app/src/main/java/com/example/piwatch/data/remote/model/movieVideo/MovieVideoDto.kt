package com.example.piwatch.data.remote.model.movieVideo


import com.google.gson.annotations.SerializedName

data class MovieVideoDto(
    @SerializedName("results")
    val results: List<Result>
)