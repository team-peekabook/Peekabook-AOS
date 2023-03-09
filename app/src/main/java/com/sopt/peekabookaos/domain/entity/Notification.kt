package com.sopt.peekabookaos.domain.entity

data class Notification(
    val alarmId: Int,
    val typeId: Int,
    val senderId: Int,
    val senderName: String,
    val profileImage: String,
    val createdAt: String,
    val bookTitle: String = ""
)
