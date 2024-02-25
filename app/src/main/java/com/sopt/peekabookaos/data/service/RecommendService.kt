package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.RecommendationRequest
import com.sopt.peekabookaos.data.entity.response.RecommendResponse
import com.sopt.peekabookaos.data.entity.response.RecommendationResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecommendService {
    @GET("recommend")
    suspend fun getRecommend(): BaseResponse<RecommendResponse>

    @POST("friend/{friendId}/recommend")
    suspend fun postRecommendation(
        @Body body: RecommendationRequest,
        @Path("friendId") friendId: Int
    ): BaseResponse<RecommendationResponse>

    @DELETE("recommend/{recommendId}")
    suspend fun deleteRecommend(
        @Path("recommendId") recommendId: Int
    ): BaseResponse<NoResponse>
}
