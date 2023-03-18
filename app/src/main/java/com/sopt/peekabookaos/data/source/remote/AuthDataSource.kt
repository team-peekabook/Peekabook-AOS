package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.LoginResponse
import com.sopt.peekabookaos.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun postLogin(socialPlatform: String): BaseResponse<LoginResponse> =
        authService.postLogin(socialPlatform)
}
