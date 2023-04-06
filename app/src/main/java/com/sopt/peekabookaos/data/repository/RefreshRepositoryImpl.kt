package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.local.LocalPrefDataSource
import com.sopt.peekabookaos.data.source.remote.RefreshDataSource
import com.sopt.peekabookaos.domain.repository.RefreshRepository
import javax.inject.Inject

class RefreshRepositoryImpl @Inject constructor(
    private val refreshDataSource: RefreshDataSource,
    private val localPrefDataSource: LocalPrefDataSource
) : RefreshRepository {
    override suspend fun getRefreshToken(refreshToken: String): Result<Unit> =
        kotlin.runCatching { refreshDataSource.getRefreshToken(refreshToken) }.map { response ->
            with(requireNotNull(response.data)) {
                localPrefDataSource.accessToken = this.newAccessToken
                localPrefDataSource.refreshToken = this.refreshToken
            }
        }
}
