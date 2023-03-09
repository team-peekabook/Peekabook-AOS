package com.sopt.peekabookaos.domain.entity

data class PickModify(
    var id: Int,
    var pickIndex: Int,
    val book: Book
)
