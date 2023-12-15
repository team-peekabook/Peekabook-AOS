package com.sopt.peekabookaos.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateInformation(
    val imageUrl: String = "",
    val text: String = ""
) : Parcelable
