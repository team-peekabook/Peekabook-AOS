package com.sopt.peekabookaos.domain.entity

data class Books(
    val id: Int = -1,
    val bookId: Int = -1,
    val pickIndex: Int = -1,
    val book: Book = Book()
)
