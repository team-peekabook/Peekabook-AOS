package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.RecommendDataSource
import com.sopt.peekabookaos.domain.entity.RecommendBookList
import com.sopt.peekabookaos.domain.repository.RecommendRepository
import javax.inject.Inject

class RecommendRepositoryImpl @Inject constructor(
    private val recommendDataSource: RecommendDataSource
) : RecommendRepository {
    override suspend fun getRecommend(): Result<RecommendBookList> =
        kotlin.runCatching { recommendDataSource.getRecommend() }.map { response ->
            requireNotNull(response.data).toRecommendBookList()
        }

    override suspend fun postRecommendation(
        recommendDesc: String?,
        bookTitle: String,
        bookImage: String,
        author: String,
        publisher: String,
        friendId: Int
    ): Result<Boolean> =
        kotlin.runCatching {
            recommendDataSource.postRecommendation(
                recommendDesc,
                bookTitle,
                bookImage,
                author,
                publisher,
                friendId
            )
        }.map { response -> response.success }
}
