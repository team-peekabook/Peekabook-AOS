package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.local.LocalPrefDataSource
import com.sopt.peekabookaos.data.source.remote.AuthDataSource
import com.sopt.peekabookaos.domain.entity.Login
import com.sopt.peekabookaos.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localPrefDataSource: LocalPrefDataSource
) : AuthRepository {
    override suspend fun postLogin(socialPlatform: String): Result<Login> =
        kotlin.runCatching { authDataSource.postLogin(socialPlatform) }.map { response ->
            requireNotNull(response.data).toLogin()
        }

    override fun initToken(accessToken: String, refreshToken: String) {
        localPrefDataSource.accessToken = accessToken
        localPrefDataSource.refreshToken = refreshToken
    }

    override fun clearLocalPref() {
        localPrefDataSource.clearLocalPref()
    }
}
