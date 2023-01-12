package com.sopt.peekabookaos.data.entity.response

import android.os.Parcelable
import com.sopt.peekabookaos.data.entity.Book
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class DetailResponse(
    val description: String,
    val memo: String,
    @SerialName("Book")
    val book: Book
) : Parcelable
