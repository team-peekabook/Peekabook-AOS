package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.Book
import kotlinx.serialization.Serializable

@Serializable
data class BookEntity(
    val id: Int = -1,
    val bookImage: String = "",
    val bookTitle: String = "",
    val author: String = ""
) {
    fun toBook(): Book = Book(
        id = this.id,
        bookImage = this.bookImage,
        bookTitle = this.bookTitle,
        author = this.author
    )
}
