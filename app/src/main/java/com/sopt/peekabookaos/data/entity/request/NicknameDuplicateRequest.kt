package com.sopt.peekabookaos.data.entity.request
import kotlinx.serialization.Serializable

@Serializable
data class NicknameDuplicateRequest(
    val nickname: String
)
