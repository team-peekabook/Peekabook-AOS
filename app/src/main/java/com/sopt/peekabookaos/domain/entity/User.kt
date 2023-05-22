package com.sopt.peekabookaos.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int = -1,
    val nickname: String = "",
    val profileImage: String? = null,
    val intro: String = "",
    val isFollowed: Boolean = false,
    val isBlocked: Boolean = false
) : Parcelable
