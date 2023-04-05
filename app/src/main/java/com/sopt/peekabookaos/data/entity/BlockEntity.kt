package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.FriendList
import kotlinx.serialization.Serializable

@Serializable
data class BlockEntity(
    val id: Int,
    val nickname: String,
    val profileImage: String
) {
    fun toBlock(): FriendList = FriendList(
        id = this.id,
        nickname = this.nickname,
        profileImage = this.profileImage
    )
}
