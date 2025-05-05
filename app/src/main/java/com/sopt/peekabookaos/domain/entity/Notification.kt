package com.sopt.peekabookaos.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Notification(
    val alarmId: Int = -1,
    val typeId: Int = -1,
    val senderId: Int = -1,
    val senderName: String = "",
    val profileImage: String? = null,
    val createdAt: String = "",
    val bookTitle: String = ""
) : Parcelable
