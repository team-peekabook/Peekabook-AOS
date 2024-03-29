package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.RecommendBookList

interface RecommendRepository {
    suspend fun getRecommend(): Result<RecommendBookList>

    suspend fun postRecommendation(
        recommendDesc: String?,
        bookTitle: String,
        bookImage: String,
        author: String,
        publisher: String,
        friendId: Int
    ): Result<Boolean>

    suspend fun deleteRecommend(recommendId: Int): Result<Unit>
}
