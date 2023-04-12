package com.sopt.peekabookaos.domain.entity

data class FriendShelf(
    val myIntro: User = User(),
    val user: List<User> = emptyList(),
    val friendIntro: User = User(),
    val picks: List<Picks> = emptyList(),
    val bookTotalNum: Int = -1,
    val books: List<Books> = emptyList()
)
