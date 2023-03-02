package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.request.RecommendationRequest
import com.sopt.peekabookaos.data.entity.response.Recommend
import com.sopt.peekabookaos.data.source.remote.RecommendDataSource
import com.sopt.peekabookaos.domain.entity.Recommendation
import com.sopt.peekabookaos.domain.repository.RecommendRepository
import javax.inject.Inject

class RecommendRepositoryImpl @Inject constructor(
    private val recommendDataSource: RecommendDataSource
) : RecommendRepository {
    override suspend fun getRecommend(): Result<Recommend> =
        kotlin.runCatching { recommendDataSource.getRecommend() }.map { response ->
            requireNotNull(response.data).toRecommend()
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
