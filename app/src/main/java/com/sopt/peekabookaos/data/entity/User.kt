package com.sopt.peekabookaos.data.entity

data class User(
    val id: Int = -1,
    val nickname: String = "",
    val profileImage: String = "",
    val intro: String = "",
    val isFollowed: Boolean = false
)
