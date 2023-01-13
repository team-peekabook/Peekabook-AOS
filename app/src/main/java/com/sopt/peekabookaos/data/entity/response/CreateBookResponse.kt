package com.sopt.peekabookaos.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBookResponse(
    @SerialName("id")
    val bookId: Int
)
