package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.local.LocalPrefDataSource
import com.sopt.peekabookaos.data.source.remote.AuthDataSource
import com.sopt.peekabookaos.domain.entity.Token
import com.sopt.peekabookaos.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localPrefDataSource: LocalPrefDataSource
) : AuthRepository {
    override suspend fun postLogin(socialPlatform: String): Result<Token> =
        kotlin.runCatching { authDataSource.postLogin(socialPlatform) }.map { response ->
            requireNotNull(response.data).toToken()
        }

    override suspend fun deleteUser(): Result<Boolean> =
        kotlin.runCatching { authDataSource.deleteUser() }.map { response -> response.success }

    override fun initToken(accessToken: String, refreshToken: String) {
        localPrefDataSource.accessToken = accessToken
        localPrefDataSource.refreshToken = refreshToken
    }

    override fun clearLocalPref() {
        localPrefDataSource.clearLocalPref()
    }
}
