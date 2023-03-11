package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class BookEntity(
    val id: Int = -1,
    val bookImage: String = "",
    val bookTitle: String = "",
    val author: String = ""
)
