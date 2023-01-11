package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val alarmId: Int,
    val typeId: Int,
    val senderId: Int,
    val senderName: String,
    val profileImage: String,
    val createdAt: String,
    val bookTitle: String = ""
)
