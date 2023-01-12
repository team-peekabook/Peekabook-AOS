package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.request.RecommendationRequest
import com.sopt.peekabookaos.data.entity.response.RecommendResponse
import com.sopt.peekabookaos.data.entity.response.RecommendationResponse
import com.sopt.peekabookaos.data.source.remote.RecommendDataSource
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
    ): Result<RecommendationResponse> =
        kotlin.runCatching {
            recommendDataSource.postRecommendation(
                recommendationRequest,
                friendId
            )
        }
            .map { response ->
                response.data!!
            }
}
