package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.domain.entity.Token
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val isSignedUp: Boolean
) {
    fun toToken(): Token = Token(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        isSignedUp = this.isSignedUp
    )
}
