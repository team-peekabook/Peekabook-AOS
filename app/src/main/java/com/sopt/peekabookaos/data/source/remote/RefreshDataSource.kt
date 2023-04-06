package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.RefreshResponse
import com.sopt.peekabookaos.data.service.RefreshService
import javax.inject.Inject

class RefreshDataSource @Inject constructor(
    private val refreshService: RefreshService
) {
    suspend fun getRefreshToken(refreshToken: String): BaseResponse<RefreshResponse> =
        refreshService.getRefreshToken(refreshToken)
}
