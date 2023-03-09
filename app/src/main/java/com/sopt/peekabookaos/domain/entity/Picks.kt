package com.sopt.peekabookaos.domain.entity

import com.sopt.peekabookaos.data.entity.BookEntity

class Picks(
    val id: Int,
    val pickIndex: Int,
    val book: BookEntity,
    val description: String?
)
