package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.RecommendResponse
import retrofit2.http.GET

interface RecommendService {
    @GET("recommend")
    suspend fun getRecommend(): BaseResponse<RecommendResponse>
}
