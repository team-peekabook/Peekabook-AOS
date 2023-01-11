package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.Books
import com.sopt.peekabookaos.data.entity.FriendList
import com.sopt.peekabookaos.data.entity.Picks
import com.sopt.peekabookaos.data.entity.SelfIntro
import kotlinx.serialization.Serializable

@Serializable
data class MyShelfResponse(
    val friendList: List<FriendList>,
    val myIntro: SelfIntro,
    val picks: List<Picks>,
    val bookTotalNum: Int,
    val books: List<Books>
)
