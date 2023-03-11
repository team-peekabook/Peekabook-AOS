package com.sopt.peekabookaos.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelfIntro(
    val id: Int = -1,
    val nickname: String = "",
    val profileImage: String = "",
    val intro: String = ""
) : Parcelable
