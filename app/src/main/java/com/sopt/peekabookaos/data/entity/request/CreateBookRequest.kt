package com.sopt.peekabookaos.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateBookRequest(
    val bookImage: String,
    val bookTitle: String,
    val author: String,
    val description: String?,
    val memo: String?
)
