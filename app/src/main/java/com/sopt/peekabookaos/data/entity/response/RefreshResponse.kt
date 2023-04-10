package com.sopt.peekabookaos.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponse(
    val newAccessToken: String,
    val refreshToken: String
)
