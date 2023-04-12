package com.sopt.peekabookaos.domain.entity

data class MyShelf(
    val user: List<User> = emptyList(),
    val myIntro: User = User(),
    val picks: List<Picks> = emptyList(),
    val bookTotalNum: Int = -1,
    val books: List<Books> = emptyList()
)
