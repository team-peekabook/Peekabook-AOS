package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.FriendProfile
import kotlinx.serialization.Serializable

@Serializable
data class FriendListEntity(
    val id: Int,
    val nickname: String,
    val profileImage: String
) {
    fun toFriendList(): FriendProfile = FriendProfile(
        id = this.id,
        nickname = this.nickname,
        profileImage = this.profileImage
    )
}
