package com.sopt.peekabookaos.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FriendUser(
    var name: String,
    val profile: String,
    val comment: String
) : Parcelable
