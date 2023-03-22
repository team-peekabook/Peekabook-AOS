package com.sopt.peekabookaos.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Int = -1,
    val bookImage: String = "",
    val bookTitle: String = "",
    val author: String = "",
    val publisher: String = ""
) : Parcelable
