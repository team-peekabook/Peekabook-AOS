package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.BooksEntity
import com.sopt.peekabookaos.data.entity.FriendListEntity
import com.sopt.peekabookaos.data.entity.PicksEntity
import com.sopt.peekabookaos.data.entity.SelfIntroEntity
import com.sopt.peekabookaos.domain.entity.MyShelf
import kotlinx.serialization.Serializable

@Serializable
data class MyShelfResponse(
    val friendList: List<FriendListEntity>,
    val myIntro: SelfIntroEntity,
    val picks: List<PicksEntity>,
    val bookTotalNum: Int,
    val books: List<BooksEntity>
) {
    fun toMyShelf(): MyShelf = MyShelf(
        friendList = this.friendList.map { friendListEntity ->
            friendListEntity.toFriendList()
        },
        myIntro = this.myIntro.toSelfIntro(),
        picks = this.picks.map { picksEntity ->
            picksEntity.toPicks()
        },
        bookTotalNum = this.bookTotalNum,
        books = this.books.map { booksEntity ->
            booksEntity.toBooks()
        }
    )
}
