package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.Login

interface AuthRepository {
    suspend fun postLogin(socialPlatform: String): Result<Login>
}
