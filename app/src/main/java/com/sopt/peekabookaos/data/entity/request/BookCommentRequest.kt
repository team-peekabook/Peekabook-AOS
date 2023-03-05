package com.sopt.peekabookaos.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class BookCommentRequest(
    val description: String?,
    val memo: String?
)
