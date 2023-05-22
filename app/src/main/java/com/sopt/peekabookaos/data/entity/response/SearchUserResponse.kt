package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.domain.entity.User
import kotlinx.serialization.Serializable

@Serializable
data class SearchUserResponse(
    val friendId: Int,
    val nickname: String,
    val profileImage: String?,
    val isFollowed: Boolean,
    val isBlocked: Boolean
) {
    fun toUser(): User = User(
        id = this.friendId,
        nickname = this.nickname,
        profileImage = this.profileImage,
        intro = "",
        isFollowed = this.isFollowed,
        isBlocked = this.isBlocked
    )
}
