package com.sopt.peekabookaos.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val alarmId: Int,
    val profile: String,
    val senderName: String,
    val receiverId: Int,
    val senderId: Int,
    val bookTitle: String?,
    val typeId: Int,
    val createdAt: String
)
