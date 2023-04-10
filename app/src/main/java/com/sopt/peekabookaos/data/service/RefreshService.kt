package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.RefreshResponse
import retrofit2.http.GET

interface RefreshService {
    @GET("auth/token")
    suspend fun getRefreshToken(): BaseResponse<RefreshResponse>
}
