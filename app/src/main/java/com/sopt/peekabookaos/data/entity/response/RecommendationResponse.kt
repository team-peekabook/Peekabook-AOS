package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.domain.entity.Recommendation
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationResponse(
    val recommendId: Int
) {
    fun toRecommendation(): Recommendation = Recommendation(
        recommendId = this.recommendId
    )
}
