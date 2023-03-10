package com.sopt.peekabookaos.domain.entity

data class RecommendBookList(
    val recommendedBook: List<Recommend>,
    val recommendingBook: List<Recommend>
)
