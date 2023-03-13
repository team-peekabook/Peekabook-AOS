package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.RecommendBookList

interface RecommendRepository {
    suspend fun getRecommend(): Result<RecommendBookList>

    suspend fun postRecommendation(
        recommendDesc: String?,
        bookTitle: String,
        bookImage: String,
        author: String,
        friendId: Int
    ): Result<Boolean>
}
