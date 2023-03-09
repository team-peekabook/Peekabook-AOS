package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.Notification
import kotlinx.serialization.Serializable

@Serializable
data class NotificationEntity(
    val alarmId: Int,
    val typeId: Int,
    val senderId: Int,
    val senderName: String,
    val profileImage: String,
    val createdAt: String,
    val bookTitle: String = ""
) {
    fun toNotification(): Notification = Notification(
        alarmId = this.alarmId,
        typeId = this.typeId,
        senderId = this.senderId,
        senderName = this.senderName,
        profileImage = this.profileImage,
        createdAt = this.createdAt,
        bookTitle = this.bookTitle
    )
}
