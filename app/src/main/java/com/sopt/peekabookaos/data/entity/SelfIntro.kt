package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class SelfIntro(
    val id: Int = -1,
    val nickname: String = "",
    val profileImage: String = "",
    val intro: String = ""
)
