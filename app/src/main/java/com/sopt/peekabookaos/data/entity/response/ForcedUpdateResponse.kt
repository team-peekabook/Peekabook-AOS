package com.sopt.peekabookaos.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class ForcedUpdateResponse(
    val imageUrl: String,
    val androidForceVersion: String,
    val text: String
)
