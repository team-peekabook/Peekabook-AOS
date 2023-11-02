package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.VersionResponse
import retrofit2.http.GET

interface ForceUpdateService {
    @GET("user/v1/version")
    suspend fun getVersion() : BaseResponse<VersionResponse>
}