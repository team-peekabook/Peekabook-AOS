package com.sopt.peekabookaos.domain.entity

data class FriendShelf(
    val myIntro: SelfIntro,
    val friendList: List<FriendList>,
    val friendIntro: SelfIntro,
    val picks: List<Picks>,
    val bookTotalNum: Int,
    val books: List<Books>
)
