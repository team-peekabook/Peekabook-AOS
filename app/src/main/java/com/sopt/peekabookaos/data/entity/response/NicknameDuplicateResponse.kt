package com.sopt.peekabookaos.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class NicknameDuplicateResponse(
    val check: Int
)
