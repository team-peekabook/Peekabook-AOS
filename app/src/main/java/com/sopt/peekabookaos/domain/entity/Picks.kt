package com.sopt.peekabookaos.domain.entity

class Picks(
    val id: Int = -1,
    var pickIndex: Int = -1,
    val book: Book,
    val description: String? = ""
)
