package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.data.entity.request.RecommendationRequest
import com.sopt.peekabookaos.domain.entity.RecommendBookList
import com.sopt.peekabookaos.domain.entity.Recommendation

interface RecommendRepository {
    suspend fun getRecommend(): Result<RecommendBookList>

    suspend fun postRecommendation(
        recommendationRequest: RecommendationRequest,
        friendId: Int
    ): Result<Recommendation>
}
