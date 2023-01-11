package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.response.RecommendResponse

interface RecommendRepository {
    suspend fun getRecommend(): Result<RecommendResponse>
}
