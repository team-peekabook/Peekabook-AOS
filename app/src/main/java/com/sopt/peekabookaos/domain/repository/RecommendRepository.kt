package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.data.entity.response.RecommendResponse
import com.sopt.peekabookaos.domain.entity.Recommendation

interface RecommendRepository {
    suspend fun getRecommend(): Result<RecommendBookList>

    suspend fun postRecommendation(
        recommendDesc: String?,
        bookTitle: String,
        bookImage: String,
        author: String,
        friendId: Int
    ): Result<Recommendation>
}
