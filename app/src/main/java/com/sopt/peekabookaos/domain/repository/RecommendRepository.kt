package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.data.entity.request.RecommendationRequest
import com.sopt.peekabookaos.domain.entity.Recommend
import com.sopt.peekabookaos.domain.entity.Recommendation

interface RecommendRepository {
    suspend fun getRecommend(): Result<Recommend>

    suspend fun postRecommendation(
        recommendationRequest: RecommendationRequest,
        friendId: Int
    ): Result<Recommendation>
}
