package com.sopt.peekabookaos.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateUpdateBook(
    val bookImage: String,
    val bookTitle: String,
    val author: String,
    val description: String?,
    val memo: String?
) : Parcelable
