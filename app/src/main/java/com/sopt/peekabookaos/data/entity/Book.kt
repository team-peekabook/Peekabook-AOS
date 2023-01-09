package com.sopt.peekabookaos.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val bookImage: String = "",
    val bookTitle: String = "",
    val author: String = "",
    val description: String? = null,
    val memo: String? = null
) : Parcelable
