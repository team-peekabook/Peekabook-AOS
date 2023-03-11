package com.sopt.peekabookaos.domain.entity

data class Recommend(
    val recommendId: Int,
    val recommendDesc: String?,
    val createdAt: String,
    val friendId: Int,
    val friendNickname: String,
    val friendImage: String,
    val bookId: Int,
    val bookTitle: String,
    val author: String,
    val bookImage: String
)
