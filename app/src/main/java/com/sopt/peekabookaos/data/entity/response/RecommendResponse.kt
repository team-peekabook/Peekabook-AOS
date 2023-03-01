package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.Recommend
import kotlinx.serialization.Serializable

@Serializable
data class RecommendResponse(
    val recommendedBook: List<Recommend>,
    val recommendingBook: List<Recommend>
) {
    fun toRecommend(): com.sopt.peekabookaos.domain.entity.Recommend =
        com.sopt.peekabookaos.domain.entity.Recommend(
            recommendedBook = this.recommendedBook,
            recommendingBook = this.recommendingBook
        )
}
