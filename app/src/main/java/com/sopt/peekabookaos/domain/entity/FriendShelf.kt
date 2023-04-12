package com.sopt.peekabookaos.domain.entity

data class FriendShelf(
    val myIntro: SelfIntro = SelfIntro(),
    val friendProfile: List<FriendProfile> = emptyList(),
    val friendIntro: SelfIntro = SelfIntro(),
    val picks: List<Picks> = emptyList(),
    val bookTotalNum: Int = -1,
    val books: List<Books> = emptyList()
)
