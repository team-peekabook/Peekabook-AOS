package com.sopt.peekabookaos.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookComment(
    val description: String,
    val memo: String
) : Parcelable
