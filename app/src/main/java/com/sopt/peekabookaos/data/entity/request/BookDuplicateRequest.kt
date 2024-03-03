package com.sopt.peekabookaos.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class BookDuplicateRequest(
    val bookTitle: String,
    val author: String,
    val publisher: String,
)
