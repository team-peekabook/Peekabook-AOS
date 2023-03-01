package com.sopt.peekabookaos.domain.entity

import com.sopt.peekabookaos.data.entity.Books
import com.sopt.peekabookaos.data.entity.FriendList
import com.sopt.peekabookaos.data.entity.Picks
import com.sopt.peekabookaos.data.entity.SelfIntro

data class FriendShelf(
    val myIntro: SelfIntro,
    val friendList: List<FriendList>,
    val friendIntro: SelfIntro,
    val picks: List<Picks>,
    val bookTotalNum: Int,
    val books: List<Books>
)
