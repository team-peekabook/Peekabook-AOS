package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PickModify(
    var id: Int,
    var pickIndex: Int,
    @SerialName("Book")
    val book: BookEntity
)
