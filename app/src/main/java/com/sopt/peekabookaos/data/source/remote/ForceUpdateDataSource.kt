package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.VersionResponse
import com.sopt.peekabookaos.data.service.ForceUpdateService
import javax.inject.Inject

class ForceUpdateDataSource @Inject constructor(
    private val forceUpdateService: ForceUpdateService
) {
    suspend fun getVersion(): BaseResponse<VersionResponse> =
        forceUpdateService.getVersion()
}