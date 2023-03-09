package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.PickModify
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PickModifyEntity(
    var id: Int,
    var pickIndex: Int,
    @SerialName("Book")
    val book: BookEntity
) {
    fun toPickModify(): PickModify = PickModify(
        id = this.id,
        pickIndex = this.pickIndex,
        book = this.book.toBook()
    )
}
