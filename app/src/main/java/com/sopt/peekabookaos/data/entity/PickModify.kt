package com.sopt.peekabookaos.data.entity

data class PickModify(
    val pickIndex: Int,
    val book: Book
) {
    @kotlinx.serialization.Serializable
    data class Book(
        val id: Int,
        val bookImage: String
    )
}
