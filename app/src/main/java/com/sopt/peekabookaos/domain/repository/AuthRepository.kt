package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.Token

interface AuthRepository {
    suspend fun postLogin(socialPlatform: String): Result<Token>

    suspend fun deleteUser(): Result<Boolean>

    fun initToken(accessToken: String, refreshToken: String)

    fun clearLocalPref()
}
