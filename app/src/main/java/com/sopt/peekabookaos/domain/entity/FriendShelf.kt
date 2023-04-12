package com.sopt.peekabookaos.domain.entity

data class FriendShelf(
    val myIntro: Profile = Profile(),
    val friendProfile: List<FriendProfile> = emptyList(),
    val friendIntro: Profile = Profile(),
    val picks: List<Picks> = emptyList(),
    val bookTotalNum: Int = -1,
    val books: List<Books> = emptyList()
)
