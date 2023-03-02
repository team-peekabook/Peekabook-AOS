package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.RecommendEntity
import kotlinx.serialization.Serializable

@Serializable
data class RecommendResponse(
    val recommendedBook: List<RecommendEntity>,
    val recommendingBook: List<RecommendEntity>
) {
    fun toRecommend(): Recommend = Recommend(
        recommendedBook = recommendedBook,
        recommendingBook = recommendingBook
    )
}
