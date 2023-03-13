package com.sopt.peekabookaos.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class EditBookRequest(
    val description: String?,
    val memo: String?
)
