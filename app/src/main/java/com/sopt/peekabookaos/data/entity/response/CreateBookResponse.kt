package com.sopt.peekabookaos.data.entity.response

import kotlinx.serialization.Serializable

@Serializable

data class CreateBookResponse(
    val bookId: Int,
    val bookshelfId: Int
)
