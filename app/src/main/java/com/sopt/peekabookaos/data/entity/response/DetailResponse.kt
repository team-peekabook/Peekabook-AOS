package com.sopt.peekabookaos.data.entity.response

import android.os.Parcelable
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.domain.entity.Detail
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class DetailResponse(
    val description: String?,
    val memo: String?,
    val book: Book
) : Parcelable {
    fun detail(): Detail = Detail(
        description = this.description,
        memo = this.memo,
        book = this.book
    )
}
