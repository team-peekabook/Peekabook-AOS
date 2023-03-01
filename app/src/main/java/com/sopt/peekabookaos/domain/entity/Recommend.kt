package com.sopt.peekabookaos.domain.entity

import com.sopt.peekabookaos.data.entity.Recommend

data class Recommend(
    val recommendedBook: List<Recommend>,
    val recommendingBook: List<Recommend>
)
