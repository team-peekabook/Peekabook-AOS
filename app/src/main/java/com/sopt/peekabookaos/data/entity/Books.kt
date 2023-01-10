package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Books(
    val id: Int,
    val bookId: Int,
    val pickIndex: Int,
    @SerialName("Book")
    val book: Book
)
