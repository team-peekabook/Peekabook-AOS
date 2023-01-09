package com.sopt.peekabookaos.data.entity

import java.util.Date

data class Notification(
    val alarmId: Int,
    val receiverId: Int,
    val senderId: Int,
    val bookTitle: String,
    val typeId: Int,
    val createdAt: Date
)
