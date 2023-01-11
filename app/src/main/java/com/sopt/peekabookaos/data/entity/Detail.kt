package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Detail(
    val description: String,
    val memo: String,
    val book: Book
)
