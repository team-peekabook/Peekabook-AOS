package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.ForcedUpdateResponse
import com.sopt.peekabookaos.data.service.ForcedUpdateService
import javax.inject.Inject

class ForcedUpdateDataSource @Inject constructor(
    private val forcedUpdateService: ForcedUpdateService
) {
    suspend fun getForcedUpdateVersion(): BaseResponse<ForcedUpdateResponse> =
        forcedUpdateService.getVersion()
}
