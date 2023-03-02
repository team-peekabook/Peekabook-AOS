package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.RecommendEntity
import com.sopt.peekabookaos.domain.entity.Recommend
import kotlinx.serialization.Serializable

@Serializable
data class RecommendResponse(
    val recommendedBook: List<RecommendEntity>,
    val recommendingBook: List<RecommendEntity>
) {
    fun toRecommend(): Recommend = Recommend(
        recommendedBook = this.recommendedBook,
        recommendingBook = this.recommendingBook
    )
}
