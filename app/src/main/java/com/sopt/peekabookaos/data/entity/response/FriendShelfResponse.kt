package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.Books
import com.sopt.peekabookaos.data.entity.FriendList
import com.sopt.peekabookaos.data.entity.Picks
import com.sopt.peekabookaos.data.entity.SelfIntro
import com.sopt.peekabookaos.domain.entity.FriendShelf
import kotlinx.serialization.Serializable

@Serializable
data class FriendShelfResponse(
    val myIntro: SelfIntro,
    val friendList: List<FriendList>,
    val friendIntro: SelfIntro,
    val picks: List<Picks>,
    val bookTotalNum: Int,
    val books: List<Books>
) {
    fun toFriendShelf(): FriendShelf = FriendShelf(
        myIntro = this.myIntro,
        friendList = this.friendList,
        friendIntro = this.friendIntro,
        picks = this.picks,
        bookTotalNum = this.bookTotalNum,
        books = this.books
    )
}
