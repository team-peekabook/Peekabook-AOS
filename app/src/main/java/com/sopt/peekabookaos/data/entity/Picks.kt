package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Picks(
    val pickIndex: Int,
    @SerialName("Book")
    val book: Book,
    val description: String
)
// {
//    @Serializable
//    data class Book(
//        val id: Int,
//        val bookImage: String,
//        val bookTitle: String
//    )
// }
