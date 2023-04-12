package com.sopt.peekabookaos.domain.entity

data class MyShelf(
    val friendProfile: List<FriendProfile> = emptyList(),
    val myIntro: SelfIntro = SelfIntro(),
    val picks: List<Picks> = emptyList(),
    val bookTotalNum: Int = -1,
    val books: List<Books> = emptyList()
)
