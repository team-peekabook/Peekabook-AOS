package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.entity.Token

interface AuthRepository {
    suspend fun postLogin(socialPlatform: String): Result<Token>

    fun initToken(accessToken: String, refreshToken: String)

    fun setSplashState(splashState: SplashState)

    fun getSplashState(): SplashState

    fun clearLocalPref()
}
