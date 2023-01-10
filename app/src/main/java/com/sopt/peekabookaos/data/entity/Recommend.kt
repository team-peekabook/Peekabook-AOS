package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Recommend(
    val recommendId: Int,
    val recommendDesc: String?,
    val createdAt: String,
    val friendId: Int,
    val friendNickname: String,
    val bookId: Int,
    val bookTitle: String,
    val author: String,
    val bookImage: String,
    val profileImage: String = ""
)
