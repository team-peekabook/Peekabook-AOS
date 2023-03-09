package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.domain.entity.Book
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBookResponse(
    @SerialName("id")
    val bookId: Int
) {
    fun toBook(): Book = Book(
        id = this.bookId
    )
}
