package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.User
import kotlinx.serialization.Serializable

@Serializable
data class FriendProfileEntity(
    val id: Int,
    val nickname: String,
    val profileImage: String?
) {
    fun toFriendProfile(): User = User(
        id = this.id,
        nickname = this.nickname,
        profileImage = this.profileImage
    )
}
