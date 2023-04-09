package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.local.LocalTokenDataSource
import com.sopt.peekabookaos.data.source.remote.RefreshDataSource
import com.sopt.peekabookaos.domain.repository.RefreshRepository
import javax.inject.Inject

class RefreshRepositoryImpl @Inject constructor(
    private val refreshDataSource: RefreshDataSource,
    private val localTokenDataSource: LocalTokenDataSource
) : RefreshRepository {
    override suspend fun getRefreshToken(): Result<Unit> =
        kotlin.runCatching { refreshDataSource.getRefreshToken() }.map { response ->
            with(requireNotNull(response.data)) {
                localTokenDataSource.accessToken = this.newAccessToken
                localTokenDataSource.refreshToken = this.refreshToken
            }
        }
}
