package com.sopt.peekabookaos.domain.entity

data class Token(
    val accessToken: String = "",
    val refreshToken: String = "",
    val isSignedUp: Boolean = false
)
