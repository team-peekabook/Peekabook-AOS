package com.sopt.peekabookaos.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class BookDuplicateResponse(
    val isDuplicate: Boolean
)
