package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Picks(
    val id: Int,
    val pickIndex: Int,
    @SerialName("Book")
    val book: BookEntity,
    val description: String?
)
