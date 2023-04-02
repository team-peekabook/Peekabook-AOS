package com.sopt.peekabookaos.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val socialPlatform: String
)
