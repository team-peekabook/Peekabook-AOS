package com.sopt.peekabookaos.domain.entity

data class Token(
    val accessToken: String = "",
    val refreshToken: String = "",
    val fcmToke: String = "",
    val isSignedUp: Boolean = false
)
