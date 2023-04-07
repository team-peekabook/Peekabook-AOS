package com.sopt.peekabookaos.domain.repository

interface RefreshRepository {
    suspend fun getRefreshToken(refreshToken: String): Result<Unit>
}
