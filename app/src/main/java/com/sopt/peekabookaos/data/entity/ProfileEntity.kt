package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.Profile
import kotlinx.serialization.Serializable

@Serializable
data class ProfileEntity(
    val id: Int = -1,
    val nickname: String = "",
    val profileImage: String = "",
    val intro: String = ""
) {
    fun toProfile(): Profile = Profile(
        id = this.id,
        nickname = this.nickname,
        profileImage = this.profileImage,
        intro = this.intro
    )
}
