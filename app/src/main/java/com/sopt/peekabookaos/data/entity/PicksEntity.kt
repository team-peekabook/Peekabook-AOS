package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.Picks
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PicksEntity(
    val id: Int,
    val pickIndex: Int,
    @SerialName("Book")
    val book: BookEntity,
    val description: String?
) {
    fun toPicks(): Picks = Picks(
        id = this.id,
        pickIndex = this.pickIndex,
        book = this.book,
        description = this.description
    )
}
