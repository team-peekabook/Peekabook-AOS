package com.sopt.peekabookaos.domain.entity

data class RecommendBookList(
    val recommendedBook: List<Recommend> = emptyList(),
    val recommendingBook: List<Recommend> = emptyList()
)
