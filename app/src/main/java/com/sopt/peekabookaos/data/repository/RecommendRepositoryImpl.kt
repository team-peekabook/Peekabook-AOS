package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.response.RecommendResponse
import com.sopt.peekabookaos.data.source.remote.RecommendDataSource
import javax.inject.Inject

class RecommendRepositoryImpl @Inject constructor(
    private val recommendDataSource: RecommendDataSource
) : RecommendRepository {
    override suspend fun getRecommend(): Result<RecommendResponse> =
        kotlin.runCatching { recommendDataSource.getRecommend() }.map { response ->
            response.data!!
        }
}
