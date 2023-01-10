package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T? = null
)
