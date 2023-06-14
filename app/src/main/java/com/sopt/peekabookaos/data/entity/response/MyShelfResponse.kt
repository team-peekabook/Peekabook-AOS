package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.BooksEntity
import com.sopt.peekabookaos.data.entity.FriendProfileEntity
import com.sopt.peekabookaos.data.entity.PicksEntity
import com.sopt.peekabookaos.data.entity.ProfileEntity
import com.sopt.peekabookaos.domain.entity.Shelf
import kotlinx.serialization.Serializable

@Serializable
data class MyShelfResponse(
    val friendList: List<FriendProfileEntity>,
    val myIntro: ProfileEntity,
    val picks: List<PicksEntity>,
    val bookTotalNum: Int,
    val books: List<BooksEntity>
) {
    fun toShelf(): Shelf = Shelf(
        user = this.friendList.map { friendListEntity ->
            friendListEntity.toFriendProfile()
        },
        myIntro = this.myIntro.toUser(),
        picks = this.picks.map { picksEntity ->
            picksEntity.toPicks()
        },
        bookTotalNum = this.bookTotalNum,
        books = this.books.map { booksEntity ->
            booksEntity.toBooks()
        }
    )
}
