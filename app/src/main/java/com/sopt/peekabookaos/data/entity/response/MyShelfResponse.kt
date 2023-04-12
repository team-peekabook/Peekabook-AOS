package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.BooksEntity
import com.sopt.peekabookaos.data.entity.FriendProfileEntity
import com.sopt.peekabookaos.data.entity.PicksEntity
import com.sopt.peekabookaos.data.entity.SelfIntroEntity
import com.sopt.peekabookaos.domain.entity.MyShelf
import kotlinx.serialization.Serializable

@Serializable
data class MyShelfResponse(
    val friendList: List<FriendProfileEntity>,
    val myIntro: SelfIntroEntity,
    val picks: List<PicksEntity>,
    val bookTotalNum: Int,
    val books: List<BooksEntity>
) {
    fun toMyShelf(): MyShelf = MyShelf(
        friendProfile = this.friendList.map { friendListEntity ->
            friendListEntity.toFriendProfile()
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
