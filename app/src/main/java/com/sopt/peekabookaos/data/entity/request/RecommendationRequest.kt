package com.sopt.peekabookaos.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class RecommendationRequest(
    val recommendDesc: String?,
    val bookTitle: String,
    val bookImage: String,
    val author: String
)
