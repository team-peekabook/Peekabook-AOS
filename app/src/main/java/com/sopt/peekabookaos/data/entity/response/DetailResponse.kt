package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.BookEntity
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
        book = this.book.toBook()
    )
}
