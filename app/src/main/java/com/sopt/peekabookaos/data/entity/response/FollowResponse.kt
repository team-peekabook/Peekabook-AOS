package com.sopt.peekabookaos.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class FollowResponse(
    val followId: Int,
    val receiverId: Int,
    val senderId: Int
)
