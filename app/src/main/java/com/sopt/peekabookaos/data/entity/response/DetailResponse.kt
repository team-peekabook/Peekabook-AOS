package com.sopt.peekabookaos.data.entity.response

import android.os.Parcelable
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.domain.entity.Detail
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class DetailResponse(
    val description: String?,
    val memo: String?,
    @SerialName("Book")
    val book: Book
) : Parcelable {
    fun toDetail(): Detail = Detail(
        description = this.description,
        memo = this.memo,
        book = this.book
    )
}
