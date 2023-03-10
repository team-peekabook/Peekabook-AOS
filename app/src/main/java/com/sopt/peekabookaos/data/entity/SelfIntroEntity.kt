package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.SelfIntro
import kotlinx.serialization.Serializable

@Serializable
data class SelfIntroEntity(
    val id: Int,
    val nickname: String,
    val profileImage: String,
    val intro: String
) {
    fun toSelfIntro(): SelfIntro = SelfIntro(
        id = this.id,
        nickname = this.nickname,
        profileImage = this.profileImage,
        intro = this.intro
    )
}
