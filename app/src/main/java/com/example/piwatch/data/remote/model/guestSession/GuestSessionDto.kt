package com.example.piwatch.data.remote.model.guestSession


import com.google.gson.annotations.SerializedName

data class GuestSessionDto(
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("guest_session_id")
    val guestSessionId: String,
    @SerializedName("success")
    val success: Boolean
)