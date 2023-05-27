package com.sopt.peekabookaos.domain.entity

data class Recommend(
    val recommendId: Int = -1,
    val recommendDesc: String? = null,
    val createdAt: String = "",
    val friendId: Int = -1,
    val friendNickname: String = "",
    val friendImage: String? = null,
    val bookId: Int = -1,
    val bookTitle: String = "",
    val author: String = "",
    val bookImage: String = ""
)
