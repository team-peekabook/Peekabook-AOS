package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.local.LocalPrefDataSource
import com.sopt.peekabookaos.data.source.local.LocalSignedUpDataSource
import com.sopt.peekabookaos.data.source.local.LocalTokenDataSource
import com.sopt.peekabookaos.data.source.remote.AuthDataSource
import com.sopt.peekabookaos.domain.entity.Token
import com.sopt.peekabookaos.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localTokenDataSource: LocalTokenDataSource,
    private val localPrefDataSource: LocalPrefDataSource,
    private val localSignedUpDataSource: LocalSignedUpDataSource
) : AuthRepository {
    override suspend fun postLogin(socialPlatform: String): Result<Token> =
        kotlin.runCatching { authDataSource.postLogin(socialPlatform) }.map { response ->
            requireNotNull(response.data).toToken()
        }

    override suspend fun deleteUser(): Result<Unit> =
        kotlin.runCatching { authDataSource.deleteUser() }

    override fun initToken(accessToken: String, refreshToken: String) {
        localTokenDataSource.accessToken = accessToken
        localTokenDataSource.refreshToken = refreshToken
    }

    override fun clearLocalPref() = localPrefDataSource.clearLocalPref()

    override fun getSignedUp(): Boolean = localSignedUpDataSource.isSignedUp
    override fun setSignedUp() {
        localSignedUpDataSource.isSignedUp = true
    }

}
