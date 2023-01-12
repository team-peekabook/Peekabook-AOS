package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.request.RecommendationRequest
import com.sopt.peekabookaos.data.entity.response.RecommendResponse
import com.sopt.peekabookaos.data.entity.response.RecommendationResponse

interface RecommendRepository {
    suspend fun getRecommend(): Result<RecommendResponse>

    suspend fun postRecommendation(
        recommendationRequest: RecommendationRequest,
        friendId: Int
    ): Result<RecommendationResponse>
}
