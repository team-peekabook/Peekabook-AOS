package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.RecommendEntity

data class Recommend(
    val recommendedBook: List<RecommendEntity>,
    val recommendingBook: List<RecommendEntity>
)
