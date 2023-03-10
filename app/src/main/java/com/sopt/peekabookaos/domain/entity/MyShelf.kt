package com.sopt.peekabookaos.domain.entity

data class MyShelf(
    val friendList: List<FriendList>,
    val myIntro: SelfIntro,
    val picks: List<Picks>,
    val bookTotalNum: Int = -1,
    val books: List<Books>
)
