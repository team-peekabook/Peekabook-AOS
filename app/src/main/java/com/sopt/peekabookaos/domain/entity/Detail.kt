package com.sopt.peekabookaos.domain.entity

import android.os.Parcelable
import com.sopt.peekabookaos.data.entity.Book
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Detail(
    val description: String?,
    val memo: String?,
    @SerialName("Book")
    val book: Book
) : Parcelable
