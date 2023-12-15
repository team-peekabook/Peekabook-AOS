package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.ForcedUpdateResponse
import retrofit2.http.GET

interface ForcedUpdateService {
    @GET("user/v1/version")
    suspend fun getVersion(): BaseResponse<ForcedUpdateResponse>
}
