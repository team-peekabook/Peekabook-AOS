package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.BookEntity
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.entity.Detail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailResponse(
    val description: String?,
    val memo: String?,
    @SerialName("Book")
    val book: BookEntity
) {
    fun toDetail(): Detail = Detail(
        description = this.description,
        memo = this.memo,
        /** 현재 DetailResponse의 book과 Detail의 book의 타입이 일치하지 않음. 임의로 Book() 넣어둘게요 */
        // book = this.book
        book = Book()
    )
}
