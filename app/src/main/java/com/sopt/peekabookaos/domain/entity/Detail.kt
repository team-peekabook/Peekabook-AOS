package com.sopt.peekabookaos.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(
    val description: String? = "",
    val memo: String? = "",
    val book: Book = Book()
) : Parcelable
