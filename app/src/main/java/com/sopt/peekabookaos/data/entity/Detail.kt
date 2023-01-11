package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Detail(
    val description: String,
    val memo: String,
    @SerialName("Book")
    val book: Book
)
