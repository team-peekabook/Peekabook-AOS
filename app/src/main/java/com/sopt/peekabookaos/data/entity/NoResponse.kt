package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class NoResponse(
    val status: Int,
    val success: Boolean,
    val message: String
)
