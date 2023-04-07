package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.local.LocalPrefDataSource
import com.sopt.peekabookaos.data.source.local.LocalSplashDataSource
import com.sopt.peekabookaos.data.source.local.LocalTokenDataSource
import com.sopt.peekabookaos.data.source.remote.AuthDataSource
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.entity.Token
import com.sopt.peekabookaos.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localTokenDataSource: LocalTokenDataSource,
    private val localPrefDataSource: LocalPrefDataSource,
    private val localSplashDataSource: LocalSplashDataSource
) : AuthRepository {
    override suspend fun postLogin(socialPlatform: String): Result<Token> =
        kotlin.runCatching { authDataSource.postLogin(socialPlatform) }.map { response ->
            requireNotNull(response.data).toToken()
        }

    override fun initToken(accessToken: String, refreshToken: String) {
        localTokenDataSource.accessToken = accessToken
        localTokenDataSource.refreshToken = refreshToken
    }

    override fun setSplashState(splashState: SplashState) {
        localSplashDataSource.splashState = splashState.toString()
    }

    override fun getSplashState(): SplashState {
        return SplashState.valueOf(localSplashDataSource.splashState)
    }

    override fun clearLocalPref() {
        localPrefDataSource.clearLocalPref()
    }
}
