package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.FriendList
import kotlinx.serialization.Serializable

@Serializable
data class FriendListEntity(
    val id: Int,
    val nickname: String,
    val profileImage: String
) {
    fun toFriendList(): FriendList = FriendList(
        id = this.id,
        nickname = this.nickname,
        profileImage = this.profileImage
    )
}
