package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.RecommendEntity
import com.sopt.peekabookaos.domain.entity.RecommendBookList
import kotlinx.serialization.Serializable

@Serializable
data class RecommendResponse(
    val recommendedBook: List<RecommendEntity>,
    val recommendingBook: List<RecommendEntity>
) {
    fun toRecommendBookList(): RecommendBookList = RecommendBookList(
        recommendedBook = this.recommendedBook.map { recommendEntity ->
            recommendEntity.toRecommend()
        },
        recommendingBook = this.recommendingBook.map { recommendEntity ->
            recommendEntity.toRecommend()
        }
    )
}
