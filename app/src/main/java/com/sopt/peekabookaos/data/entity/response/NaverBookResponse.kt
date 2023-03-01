package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.NaverBookEntity
import kotlinx.serialization.Serializable

@Serializable
data class NaverBookResponse(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NaverBookEntity>
)
