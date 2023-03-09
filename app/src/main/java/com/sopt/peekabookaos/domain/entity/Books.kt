package com.sopt.peekabookaos.domain.entity

import com.sopt.peekabookaos.data.entity.BookEntity

data class Books(
    val id: Int,
    val bookId: Int,
    val pickIndex: Int,
    val book: BookEntity
)
