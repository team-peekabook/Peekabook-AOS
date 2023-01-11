package com.sopt.peekabookaos.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class SelfIntro(
    val id: Int = -1,
    val nickname: String = "",
    val profileImage: String = "",
    val intro: String = ""
) : Parcelable
