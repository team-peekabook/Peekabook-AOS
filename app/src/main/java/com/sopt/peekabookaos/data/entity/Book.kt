package com.sopt.peekabookaos.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Int = -1,
    val bookImage: String = "",
    val bookTitle: String = "",
    val author: String = ""
) : Parcelable
