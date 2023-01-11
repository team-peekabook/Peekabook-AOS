package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PickModify(
    var pickIndex: Int,
    @SerialName("Book")
    val book: Book
) {
    @Serializable
    data class Book(
        val id: Int,
        val bookImage: String
    )
}
