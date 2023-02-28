package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.request.RecommendationRequest
import com.sopt.peekabookaos.data.entity.response.RecommendResponse
import com.sopt.peekabookaos.data.source.remote.RecommendDataSource
import com.sopt.peekabookaos.domain.entity.Recommendation
import com.sopt.peekabookaos.domain.repository.RecommendRepository
import javax.inject.Inject

class RecommendRepositoryImpl @Inject constructor(
    private val recommendDataSource: RecommendDataSource
) : RecommendRepository {
    override suspend fun getRecommend(): Result<RecommendResponse> =
        kotlin.runCatching { recommendDataSource.getRecommend() }.map { response ->
            response.data!!
        }

    override suspend fun postRecommendation(
        recommendationRequest: RecommendationRequest,
        friendId: Int
    ): Result<Recommendation> =
        kotlin.runCatching {
            recommendDataSource.postRecommendation(
                recommendationRequest,
                friendId
            )
        }
            .map { response ->
                requireNotNull(response.data).toRecommendation()
            }
}
