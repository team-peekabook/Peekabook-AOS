package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.Books
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksEntity(
    val id: Int,
    val bookId: Int,
    val pickIndex: Int,
    @SerialName("Book")
    val book: BookEntity
) {
    fun toBooks(): Books = Books(
        id = this.id,
        bookId = this.bookId,
        pickIndex = this.pickIndex,
        book = this.book.toBook()
    )
}
