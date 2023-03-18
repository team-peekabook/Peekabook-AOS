package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.domain.entity.Login

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val isSignedUp: Boolean
) {
    fun toLogin(): Login = Login(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        isSignedUp = this.isSignedUp
    )
}
