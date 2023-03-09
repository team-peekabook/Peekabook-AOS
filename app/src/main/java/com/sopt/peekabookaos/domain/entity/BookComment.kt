package com.sopt.peekabookaos.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookComment(
    val description: String? = "",
    val memo: String? = ""
) : Parcelable
