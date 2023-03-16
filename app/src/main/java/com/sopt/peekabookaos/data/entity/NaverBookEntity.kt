package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.Book
import kotlinx.serialization.Serializable

@Serializable
data class NaverBookEntity(
    val title: String = "",
    val link: String = "",
    val image: String = "",
    val author: String = "",
    val discount: Int = -1,
    val publisher: String = "",
    val isbn: String = "",
    val description: String = "",
    val pubdate: String = ""
) {
    fun toBook(): Book = Book(
        bookTitle = this.title,
        bookImage = this.image,
        author = this.author.replace("^", ", "),
        publisher = this.publisher
    )
}
