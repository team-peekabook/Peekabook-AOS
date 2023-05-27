package com.sopt.peekabookaos.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookComment(
    val description: String? = null,
    val memo: String? = null
) : Parcelable
