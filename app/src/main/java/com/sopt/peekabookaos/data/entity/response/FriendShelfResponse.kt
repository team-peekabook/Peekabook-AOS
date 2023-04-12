package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.BooksEntity
import com.sopt.peekabookaos.data.entity.FriendProfileEntity
import com.sopt.peekabookaos.data.entity.PicksEntity
import com.sopt.peekabookaos.data.entity.SelfIntroEntity
import com.sopt.peekabookaos.domain.entity.FriendShelf
import kotlinx.serialization.Serializable

@Serializable
data class FriendShelfResponse(
    val myIntro: SelfIntroEntity,
    val friendList: List<FriendProfileEntity>,
    val friendIntro: SelfIntroEntity,
    val picks: List<PicksEntity>,
    val bookTotalNum: Int,
    val books: List<BooksEntity>
) {
    fun toFriendShelf(): FriendShelf = FriendShelf(
        myIntro = this.myIntro.toSelfIntro(),
        friendProfile = this.friendList.map { friendListEntity ->
            friendListEntity.toFriendProfile()
        },
        friendIntro = this.friendIntro.toSelfIntro(),
        picks = this.picks.map { picksEntity ->
            picksEntity.toPicks()
        },
        bookTotalNum = this.bookTotalNum,
        books = this.books.map { booksEntity ->
            booksEntity.toBooks()
        }
    )
}
