package com.sopt.peekabookaos.domain.entity

data class Login(
    val accessToken: String = "",
    val refreshToken: String = "",
    val isSignedUp: Boolean = false
)
