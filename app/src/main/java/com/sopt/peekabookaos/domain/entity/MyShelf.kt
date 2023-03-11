package com.sopt.peekabookaos.domain.entity

data class MyShelf(
    val friendList: List<FriendList> = emptyList(),
    val myIntro: SelfIntro = SelfIntro(),
    val picks: List<Picks> = emptyList(),
    val bookTotalNum: Int = -1,
    val books: List<Books> = emptyList()
)
