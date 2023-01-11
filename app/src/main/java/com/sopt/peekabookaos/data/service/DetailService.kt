package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.DetailResponse
import retrofit2.http.GET

interface DetailService {
    @GET("detail")
    suspend fun getDetail(): BaseResponse<DetailResponse>
}
