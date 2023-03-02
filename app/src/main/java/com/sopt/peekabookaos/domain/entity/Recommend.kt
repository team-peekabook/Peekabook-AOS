package com.sopt.peekabookaos.domain.entity

import com.sopt.peekabookaos.data.entity.RecommendEntity

data class Recommend(
    val recommendedBook: List<RecommendEntity>,
    val recommendingBook: List<RecommendEntity>
)
