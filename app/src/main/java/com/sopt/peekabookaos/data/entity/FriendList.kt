package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class FriendList(
    val id: Int,
    val nickname: String,
    val profileImage: String
)
