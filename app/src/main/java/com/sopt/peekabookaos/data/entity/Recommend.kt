package com.sopt.peekabookaos.data.entity

data class Recommend(
    val recommendId: Int,
    val recommendDesc: String?,
    val recommendBy: String,
    val recommendTo: String,
    val createdAt: String,
    val bookId: Int,
    val bookTitle: String,
    val author: String,
    val image: String,
    val profileImage: String
)
