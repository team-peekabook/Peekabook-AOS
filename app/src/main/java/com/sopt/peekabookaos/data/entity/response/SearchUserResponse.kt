package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.User
import kotlinx.serialization.Serializable

@Serializable
data class SearchUserResponse(
    val friendId: Int,
    val nickname: String,
    val profileImage: String,
    val isFollowed: Boolean
    )
