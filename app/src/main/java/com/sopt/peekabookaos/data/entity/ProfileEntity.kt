package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.User
import kotlinx.serialization.Serializable

@Serializable
data class ProfileEntity(
    val id: Int = -1,
    val nickname: String = "",
    val profileImage: String? = null,
    val intro: String = ""
) {
    fun toUser(): User = User(
        id = this.id,
        nickname = this.nickname,
        profileImage = this.profileImage,
        intro = this.intro
    )
}
